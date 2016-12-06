package model.genetics.crossOver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.genetics.BiasedWighted;
import model.genetics.Dna;
import model.genetics.Provider;

public class CrossOverPerformer {

	private List<BiasedWighted> sourceNetworks;
	private Random random;
	private Provider<BiasedWighted> networkProvider;

	public CrossOverPerformer(List<BiasedWighted> networks, Provider<BiasedWighted> networkProvider) {
		this.sourceNetworks = networks;
		this.networkProvider = networkProvider;
		random = new Random();
	}

	public List<BiasedWighted> getNewCrossOver(int count) {
		List<BiasedWighted> newPopulation = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			BiasedWighted[] networks = new BiasedWighted[] { getRandomSourceNetwork(), getRandomSourceNetwork() };

			Dna newDna = crossOverNewDna(new Dna(networks[0]), new Dna(networks[1]));

			BiasedWighted newNetwork = convertDnaToNetwork(newDna);
			newPopulation.add(newNetwork);
		}
		return newPopulation;
	}

	private BiasedWighted convertDnaToNetwork(Dna newDna) {
		BiasedWighted newNetwork = networkProvider.get();
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

	private BiasedWighted getRandomSourceNetwork() {
		return sourceNetworks.get(random.nextInt(sourceNetworks.size()));
	}
}
