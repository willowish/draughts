package model.genetics.crossOver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.genetics.BiasedWeighted;
import model.genetics.Dna;
import model.genetics.Provider;

public class CrossOverPerformer {

	private Random random;
	private Provider<BiasedWeighted> networkProvider;

	public CrossOverPerformer(Provider<BiasedWeighted> networkProvider) {
		this.networkProvider = networkProvider;
		random = new Random();
	}

	public List<BiasedWeighted> getNewCrossOver(List<BiasedWeighted> sourceNetworks, int populationSize) {
		List<BiasedWeighted> newPopulation = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			BiasedWeighted[] networks = new BiasedWeighted[] { getRandomSourceNetwork(sourceNetworks),
					getRandomSourceNetwork(sourceNetworks) };

			Dna newDna = crossOverNewDna(new Dna(networks[0]), new Dna(networks[1]));

			BiasedWeighted newNetwork = convertDnaToNetwork(newDna);
			newPopulation.add(newNetwork);
		}
		return newPopulation;
	}

	private BiasedWeighted convertDnaToNetwork(Dna newDna) {
		BiasedWeighted newNetwork = networkProvider.get();
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

	private BiasedWeighted getRandomSourceNetwork(List<BiasedWeighted> sourceNetworks) {
		return sourceNetworks.get(random.nextInt(sourceNetworks.size()));
	}
}
