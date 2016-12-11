package model.genetics;

import static model.genetics.Configuration.biasesCount;
import static model.genetics.Configuration.weightsCount;
import static model.genetics.NeuronParametersGenerator.getRandomBias;
import static model.genetics.NeuronParametersGenerator.getRandomWeight;

import java.util.ArrayList;
import java.util.List;

import model.genetics.crossOver.CrossOverPerformer;
import model.genetics.mutation.Mutator;

public class PopulationGenerator {

	private Provider<BiasedWeighted> networkProvider;

	public PopulationGenerator(Provider<BiasedWeighted> networkProvider) {
		this.networkProvider = networkProvider;
	}

	public List<BiasedWeighted> generateRandomPopulation(int populationSize) {
		List<BiasedWeighted> population = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			population.add(setRandomNetworkParameters(networkProvider.get()));
		}

		return population;
	}

	public List<BiasedWeighted> generateNextGeneration(List<BiasedWeighted> sourcePopulation) {
		return generateNextGeneration(sourcePopulation, sourcePopulation.size());
	}

	public List<BiasedWeighted> generateNextGeneration(List<BiasedWeighted> sourcePopulation, int populationSize) {
		CrossOverPerformer crossOverPerformer = new CrossOverPerformer(networkProvider);
		Mutator mutator = new Mutator();

		List<BiasedWeighted> newPopulation = crossOverPerformer.getNewCrossOver(sourcePopulation, populationSize);
		newPopulation = mutator.mutate(newPopulation);

		return newPopulation;
	}

	private BiasedWeighted setRandomNetworkParameters(BiasedWeighted network) {
		double[] weights = new double[weightsCount];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = getRandomWeight();
		}

		double[] biases = new double[biasesCount];
		for (int i = 0; i < biases.length; i++) {
			biases[i] = getRandomBias();
		}
		network.setWeights(weights);
		network.setBiases(biases);
		return network;
	}
}
