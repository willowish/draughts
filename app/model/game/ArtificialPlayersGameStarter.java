package model.game;

import model.ai.ArtificialPlayer;
import model.game.entities.Color;

public class ArtificialPlayersGameStarter {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		new ArtificialPlayersGameStarter();
		System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms");
	}

	public ArtificialPlayersGameStarter() {
		int tries = 1000;
		int whiteResult = 0;

		for (int i = 0; i < tries; i++) {

			Game game = null;
			try {
				game = new Game(new ArtificialPlayer(), new ArtificialPlayer());
				game.start();
			} catch (WinException win) {
				// game.printFields();
				Color winningPlayer = game.getMovesBfs().getActualColor().getOpposite();
				System.out.println("win for " + winningPlayer);
				if (winningPlayer == Color.WHITE) {
					whiteResult++;
				}
			}
		}

		System.out.println("White " + whiteResult + ":" + (tries - whiteResult) + " Black");

	}
}
