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

			Dna[] dnaCodes = new Dna[] { new Dna(networks[0]), new Dna(networks[1]) };
			Dna newDna = createNewDna(dnaCodes);

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

	private Dna createNewDna(Dna[] dnaCodes) {
		int weightCount = dnaCodes[0].weights.length;
		int biasCount = dnaCodes[0].biases.length;
		Dna newDna = new Dna(weightCount, biasCount);
		for (int weightIdx = 0; weightIdx < weightCount; weightIdx++) {
			newDna.weights[weightIdx] = (dnaCodes[0].weights[weightIdx] + dnaCodes[1].weights[weightIdx]) / 2;
		}
		for (int biasIdx = 0; biasIdx < biasCount; biasIdx++) {
			newDna.biases[biasIdx] = (dnaCodes[0].biases[biasIdx] + dnaCodes[1].biases[biasIdx]) / 2;
		}
		return newDna;
	}

	private BiasedWighted getRandomSourceNetwork() {
		return sourceNetworks.get(random.nextInt(sourceNetworks.size()));
	}
}
