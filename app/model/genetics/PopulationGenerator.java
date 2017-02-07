package model.genetics;

import static model.genetics.Configuration.biasesCount;
import static model.genetics.Configuration.weightsCount;
import static model.genetics.NeuronParametersGenerator.getRandomBias;
import static model.genetics.NeuronParametersGenerator.getRandomWeight;

import java.util.ArrayList;
import java.util.List;

import model.ai.nn.NeuralNetwork;
import model.genetics.crossOver.CrossOverPerformer;
import model.genetics.mutation.Mutator;

public class PopulationGenerator {

	private CrossOverPerformer crossOverPerformer;
	private Mutator mutator;

	public PopulationGenerator() {
		crossOverPerformer = new CrossOverPerformer();
		mutator = new Mutator();
	}

	public List<NeuralNetwork> generateRandomPopulation(int populationSize) {
		List<NeuralNetwork> population = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			population.add(setRandomNetworkParameters(new NeuralNetwork()));
		}

		return population;
	}

	public List<NeuralNetwork> generateNextGeneration(List<NeuralNetwork> sourcePopulation) {
		return generateNextGeneration(sourcePopulation, sourcePopulation.size());
	}

	public List<NeuralNetwork> generateNextGeneration(List<NeuralNetwork> sourcePopulation, int populationSize) {

		List<NeuralNetwork> newPopulation = crossOverPerformer.getNewCrossOver(sourcePopulation, populationSize);
		newPopulation = mutator.mutate(newPopulation);

		return newPopulation;
	}

	private NeuralNetwork setRandomNetworkParameters(NeuralNetwork network) {
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
