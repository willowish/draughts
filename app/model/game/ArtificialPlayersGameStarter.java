package model.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.ai.ArtificialInteligence;
import model.ai.ArtificialPlayer;
import model.ai.nn.NeuralNetwork;
import model.game.entities.Color;
import model.genetics.BiasedWeighted;
import model.genetics.NeuralNetworkProvider;
import model.genetics.PopulationGenerator;

public class ArtificialPlayersGameStarter {

	private PopulationGenerator pg;
	private List<BiasedWeighted> population;
	private HashMap<BiasedWeighted, Integer> results;

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		ArtificialPlayersGameStarter artificialPlayersGameStarter = new ArtificialPlayersGameStarter();

		// try some iterations
		for (int i = 0; i < 100; i++) {
			artificialPlayersGameStarter.start();
		}
		// now try "smart" with random networks
		List<BiasedWeighted> smartPopulation = artificialPlayersGameStarter.population;

		ArtificialPlayersGameStarter artificialPlayersGameStarter2 = new ArtificialPlayersGameStarter();
		List<BiasedWeighted> dumbPopulation = new LinkedList<>();
		dumbPopulation.addAll(artificialPlayersGameStarter2.population);
		artificialPlayersGameStarter2.population.addAll(smartPopulation);
		artificialPlayersGameStarter2.start();

		int res = 0, res2 = 0;
		// check results of smart nn's
		for (BiasedWeighted nn : dumbPopulation) {
			System.out.print(artificialPlayersGameStarter2.results.get(nn) + " ");
			res += artificialPlayersGameStarter2.results.get(nn);
		}
		System.out.println();
		for (BiasedWeighted nn : smartPopulation) {
			System.out.print(artificialPlayersGameStarter2.results.get(nn) + " ");
			res2 += artificialPlayersGameStarter2.results.get(nn);
		}
		System.out.println(res + ":" + res2);

		System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms");
	}

	public ArtificialPlayersGameStarter() {
		pg = new PopulationGenerator(new NeuralNetworkProvider());
		population = pg.generateRandomPopulation(25);

	}

	private void start() {
		Game game = null;
		results = new HashMap<>();
		population.forEach(nn -> results.put(nn, 0));

		for (BiasedWeighted bw1 : population) {
			ArtificialInteligence player1Inteligence = new ArtificialInteligence((NeuralNetwork) bw1);
			ArtificialPlayer player1 = new ArtificialPlayer(player1Inteligence);
			for (BiasedWeighted bw2 : population) {
				ArtificialInteligence player2Inteligence = new ArtificialInteligence((NeuralNetwork) bw1);
				ArtificialPlayer player2 = new ArtificialPlayer(player2Inteligence);

				game = new Game(player1, player2);
				try {
					game.start();
				} catch (WinException w) {
					BiasedWeighted winningPlayer = w.getWinningColor() == Color.WHITE ? bw1 : bw2;
					results.put(winningPlayer, results.get(winningPlayer) + 1);
				}
			}
		}
		List<BiasedWeighted> sourcePopulation = new LinkedList<>();
		results.forEach((nn, result) -> {
			if (result > 35) {
				sourcePopulation.add(nn);
			}
		});

		population = pg.generateNextGeneration(sourcePopulation, 25);
		population.forEach(nn -> results.put(nn, 0));

	}
}