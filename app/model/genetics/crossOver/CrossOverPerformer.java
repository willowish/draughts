package model.genetics.crossOver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.ai.nn.NeuralNetwork;
import model.genetics.Dna;

public class CrossOverPerformer {

	private Random random;

	public CrossOverPerformer() {
		random = new Random();
	}

	public List<NeuralNetwork> getNewCrossOver(List<NeuralNetwork> sourceNetworks, int populationSize) {
		List<NeuralNetwork> newPopulation = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			NeuralNetwork[] networks = new NeuralNetwork[] { getRandomSourceNetwork(sourceNetworks),
					getRandomSourceNetwork(sourceNetworks) };

			Dna newDna = crossOverNewDna(new Dna(networks[0]), new Dna(networks[1]));

			NeuralNetwork newNetwork = convertDnaToNetwork(newDna);
			newPopulation.add(newNetwork);
		}
		return newPopulation;
	}

	private NeuralNetwork convertDnaToNetwork(Dna newDna) {
		NeuralNetwork newNetwork = new NeuralNetwork();
		newNetwork.setBiases(newDna.biases);
		newNetwork.setWeights(newDna.weights);
		return newNetwork;
	}

	private Dna crossOverNewDna(Dna firstDna, Dna secondDna) {
		int weightCount = firstDna.weights.length;
		int biasCount = firstDna.biases.length;
		Dna newDna = new Dna(weightCount, biasCount);

		for (int weightIdx = 0; weightIdx < weightCount; weightIdx++) {
			newDna.weights[weightIdx] = (firstDna.weights[weightIdx] + secondDna.weights[weightIdx]) / 2;
		}
		for (int biasIdx = 0; biasIdx < biasCount; biasIdx++) {
			newDna.biases[biasIdx] = (firstDna.biases[biasIdx] + secondDna.biases[biasIdx]) / 2;
		}
		return newDna;
	}

	private NeuralNetwork getRandomSourceNetwork(List<NeuralNetwork> sourceNetworks) {
		return sourceNetworks.get(random.nextInt(sourceNetworks.size()));
	}
}
