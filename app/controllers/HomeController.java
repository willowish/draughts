package controllers;

import static play.libs.Json.toJson;

import com.fasterxml.jackson.databind.JsonNode;

import model.envelope.BoardEnvelope;
import model.game.Game;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 */
public class HomeController extends Controller {

	public Result updateBoard() {
//		TODO: implement board check
		JsonNode jsonNode = request().body().asJson();
		BoardEnvelope envelope = Json.fromJson(jsonNode, BoardEnvelope.class);
		return ok(toJson(envelope));
	}

	public Result getBoard() {
//        TODO: fix this, just for mock data
		Game game = new Game();
		BoardEnvelope envelope = new BoardEnvelope(game.getBoard().getBoardFields());
		return ok(toJson(envelope));
	}

	/**
	 * An action that renders an HTML page with a welcome message. The configuration in the
	 * <code>routes</code> file means that this method will be called when the application receives
	 * a <code>GET</code> request with a path of <code>/</code>.
	 */
	public Result index() {
		return ok(index.render("Your new application is ready."));
	}

}
