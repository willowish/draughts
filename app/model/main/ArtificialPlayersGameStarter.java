package model.main;

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
import model.genetics.BiasedWeighted;
import model.genetics.Dna;
import model.genetics.NeuralNetworkProvider;
import model.genetics.PopulationGenerator;

public class ArtificialPlayersGameStarter {

	private static final String file_path = "D:/smartDna";
	private PopulationGenerator pg;
	private List<BiasedWeighted> population;
	private HashMap<BiasedWeighted, Integer> results;

	public static void main(String[] args) throws IOException {
		// load smart
		List<BiasedWeighted> smartPopulation = loadPopulation();

		long time = System.currentTimeMillis();

		ArtificialPlayersGameStarter artificialPlayersGameStarter = new ArtificialPlayersGameStarter();
		if (smartPopulation != null) {
			// artificialPlayersGameStarter.population = smartPopulation;
		}
		// try some iterations
		for (int i = 0; i < 1; i++) {
			artificialPlayersGameStarter.start();
		}
		// now try "smart" with random networks
		smartPopulation = artificialPlayersGameStarter.population;

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

		// save smart
		savePopulation(smartPopulation);
	}

	private static void savePopulation(List<BiasedWeighted> smartPopulation) throws IOException, FileNotFoundException {
		File file;
		file = new File(file_path);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();

		PrintWriter writer = new PrintWriter(file);
		smartPopulation.forEach(bw -> {
			writer.println(new Dna(bw).serialize());
		});
		writer.close();
	}

	private static List<BiasedWeighted> loadPopulation() throws FileNotFoundException, IOException {
		List<BiasedWeighted> smartPopulation = null;
		File file = new File(file_path);
		if (file.exists()) {
			smartPopulation = new LinkedList<>();

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
		}
		return smartPopulation;
	}

	public ArtificialPlayersGameStarter() {
		pg = new PopulationGenerator(new NeuralNetworkProvider());
		population = pg.generateRandomPopulation(25);

	}

	private void start() {
		results = new HashMap<>();
		population.forEach(nn -> results.put(nn, 0));

		for (BiasedWeighted bw1 : population) {
			ArtificialInteligence player1Inteligence = new ArtificialInteligence((NeuralNetwork) bw1);
			ArtificialPlayer player1 = new ArtificialPlayer(player1Inteligence);
			population.parallelStream().forEach((bw2) -> {
				ArtificialInteligence player2Inteligence = new ArtificialInteligence((NeuralNetwork) bw1);
				ArtificialPlayer player2 = new ArtificialPlayer(player2Inteligence);

				Game game = new Game(player1, player2);
				try {
					game.start();
				} catch (WinException w) {
					BiasedWeighted winningPlayer = w.getWinningColor() == Color.WHITE ? bw1 : bw2;
					results.put(winningPlayer, results.get(winningPlayer) + 1);
				}
			});
		}
		List<BiasedWeighted> sourcePopulation = new LinkedList<>();
		results.forEach((nn, result) -> {
			if (result > population.size() / 2 + population.size() / 4) {
				sourcePopulation.add(nn);
			}
		});
		if (sourcePopulation.size() < 5) {
			results.forEach((nn, result) -> {
				if (result >= population.size() / 2) {
					sourcePopulation.add(nn);
				}
			});

		}

		population = pg.generateNextGeneration(sourcePopulation, 10);
		population.addAll(pg.generateRandomPopulation(10));
		population.addAll(sourcePopulation.subList(0, 5));
		population.forEach(nn -> results.put(nn, 0));

	}
}