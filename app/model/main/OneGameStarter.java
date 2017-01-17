package model.main;

import model.ai.ArtificialInteligence;
import model.ai.ArtificialPlayer;
import model.ai.nn.NeuralNetwork;
import model.game.Game;
import model.game.WinException;
import model.genetics.BiasedWeighted;
import model.genetics.NeuralNetworkProvider;
import model.genetics.PopulationGenerator;

public class OneGameStarter {

	private PopulationGenerator pg;
	private BiasedWeighted player2nn;
	private BiasedWeighted player1nn;

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			OneGameStarter oneGameStarter = new OneGameStarter();
			oneGameStarter.start();
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms");
	}

	public OneGameStarter() {
		pg = new PopulationGenerator(new NeuralNetworkProvider());
		player1nn = pg.generateRandomPopulation(1).get(0);
		player2nn = pg.generateRandomPopulation(1).get(0);

	}

	private void start() {

		ArtificialInteligence player1Inteligence = new ArtificialInteligence((NeuralNetwork) player1nn);
		ArtificialPlayer player1 = new ArtificialPlayer(player1Inteligence);

		ArtificialInteligence player2Inteligence = new ArtificialInteligence((NeuralNetwork) player2nn);
		ArtificialPlayer player2 = new ArtificialPlayer(player2Inteligence);

		Game game = new Game(player1, player2);

		try {
			game.start();
		} catch (WinException w) {
			System.out.println(w.getWinningColor());
			game.printFields();
		}

	}
}