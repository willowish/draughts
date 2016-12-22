package controllers;

import static play.libs.Json.*;

import java.util.Arrays;

import model.ai.ArtificialInteligence;
import model.envelope.BoardEnvelope;
import model.game.Game;
import model.game.entities.Color;
import model.game.entities.MovesBfs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

	private Game game;
	private ArtificialInteligence ai;

	public Result updateBoard() {
		BoardEnvelope envelope = Json.fromJson(request().body().asJson(), BoardEnvelope.class);

		MovesBfs movesBfs = new MovesBfs(game.getBoard());
		movesBfs.generateNextStep(Color.WHITE);

		for (MovesBfs nextMove : movesBfs.nextSteps) {
			if (Arrays.deepEquals(envelope.board, nextMove.board.fields)) {

				nextMove.generateNextStep(Color.BLACK);
				double bestMark = Double.NEGATIVE_INFINITY;
				MovesBfs bestMove = null;
				for (MovesBfs aiNextMove : nextMove.nextSteps) {
					double mark = ai.evaluate(aiNextMove.board);
					if (bestMark < mark) {
						bestMark = mark;
						bestMove = aiNextMove;
					}
				}
				if (bestMove == null) {
					envelope = new WinEnvelope();
					return ok(toJson(envelope));
				}
				game.getBoard().setFields(bestMove.board.fields);
			}
		}
		envelope = new BoardEnvelope(game.getBoard().fields);
		return ok(toJson(envelope));
	}

	public Result getBoard() {
		game = new Game();
		ai = new ArtificialInteligence();

		BoardEnvelope envelope = new BoardEnvelope(game.getBoard().fields);
		return ok(toJson(envelope));
	}

	public Result index() {
		return ok(views.html.index.render("Your new application is ready."));
	}

}
