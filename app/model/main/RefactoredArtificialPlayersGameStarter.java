package model.main;

import static java.util.stream.Collectors.toList;
import static model.genetics.Configuration.NUMBER_OF_BEST_NN_TO_RETAIN;
import static model.genetics.Configuration.NUMBER_OF_RANDOMS_IN_NEW_POPULATION;
import static model.genetics.Configuration.NUMBER_OF_TRANSMUTED;
import static model.genetics.Configuration.POPULATION_SIZE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.ai.ArtificialInteligence;
import model.ai.ArtificialPlayer;
import model.ai.nn.NeuralNetwork;
import model.game.Game;
import model.game.WinException;
import model.game.entities.Color;
import model.genetics.Dna;
import model.genetics.PopulationGenerator;

public class RefactoredArtificialPlayersGameStarter {

	private static final int ROUNDS_TO_PLAY = 100;
	private static final String file_path = "D:/smartDna2";
	private PopulationGenerator pg;
	private List<ArtificialPlayer> population;
	private HashMap<ArtificialPlayer, Integer> results;

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		RefactoredArtificialPlayersGameStarter starter = new RefactoredArtificialPlayersGameStarter();
		for (int i = 0; i < ROUNDS_TO_PLAY; i++) {
			starter.startRound();
			starter.nextGeneration();
			System.out.println(i);
		}
		starter.savePopulation();

		System.out.println(System.currentTimeMillis() - time);
	}

	public RefactoredArtificialPlayersGameStarter() {
		results = new HashMap<>();
		pg = new PopulationGenerator();

		List<NeuralNetwork> populationNN = loadPopulation();
		if (populationNN == null) {
			populationNN = pg.generateRandomPopulation(POPULATION_SIZE);
		}

		population = networkToPlayer(populationNN);
		population.forEach(player -> results.put(player, 0));

	}

	public void savePopulation() {
		savePopulation(playerToNetwork(population));

	}

	public void nextGeneration() {
		population.sort((p1, p2) -> results.get(p1) - results.get(p2));
		List<ArtificialPlayer> masters = population.subList(0, NUMBER_OF_BEST_NN_TO_RETAIN);
		List<NeuralNetwork> transmuted = pg.generateNextGeneration(playerToNetwork(masters), NUMBER_OF_TRANSMUTED);
		List<NeuralNetwork> randoms = pg.generateRandomPopulation(NUMBER_OF_RANDOMS_IN_NEW_POPULATION);
		population = masters;
		population.addAll(networkToPlayer(transmuted));
		population.addAll(networkToPlayer(randoms));

		results.clear();
		population.forEach(player -> results.put(player, 0));
	}

	public void startRound() {
		for (int i = 0; i < population.size(); i++) {
			// kazdy z kazdym tylko raz
			for (int j = i + 1; j < population.size(); j++) {

				ArtificialPlayer player1 = population.get(i);
				ArtificialPlayer player2 = population.get(j);

				try {
					Game game = new Game(player1, player2);
					game.printFields();
					game.start();
				} catch (WinException w) {
					if (w.getWinningColor() == Color.WHITE) {
						results.put(player1, results.get(player1) + 1);
						results.put(player2, results.get(player2) - 1);
					} else {
						results.put(player2, results.get(player2) + 1);
						results.put(player1, results.get(player1) - 1);
					}
				}

			}
		}
	}

	private List<NeuralNetwork> playerToNetwork(List<ArtificialPlayer> population2) {
		return population2.stream().map(player -> player.getAi().getNeuralNetwork()).collect(toList());
	}

	private List<ArtificialPlayer> networkToPlayer(List<NeuralNetwork> populationNN) {
		return populationNN.stream().map(nn -> new ArtificialPlayer(new ArtificialInteligence(nn))).collect(toList());
	}

	private void savePopulation(List<NeuralNetwork> smartPopulation) {
		File file;
		file = new File(file_path);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();

			PrintWriter writer = new PrintWriter(file);
			smartPopulation.forEach(bw -> {
				writer.println(new Dna(bw).serialize());
			});
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<NeuralNetwork> loadPopulation() {
		List<NeuralNetwork> smartPopulation = null;
		File file = new File(file_path);
		if (file.exists()) {
			smartPopulation = new LinkedList<>();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String str;
				while ((str = reader.readLine()) != null) {
					Dna dna = Dna.deserialize(str);

					NeuralNetwork newNetwork = new NeuralNetwork();
					newNetwork.setBiases(dna.biases);
					newNetwork.setWeights(dna.weights);

					smartPopulation.add(newNetwork);
				}

				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return smartPopulation;
	}
}