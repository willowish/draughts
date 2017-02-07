package model.genetics.mutation;

import static model.genetics.Configuration.PROBABILITY_OF_MUTATION;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

import model.ai.nn.NeuralNetwork;
import model.genetics.Dna;
import model.genetics.NeuronParametersGenerator;

public class Mutator {

	private Random random;

	public Mutator() {
		random = new Random();
	}

	public List<NeuralNetwork> mutate(List<NeuralNetwork> networks) {
		for (int i = 0; i < networks.size(); i++) {
			networks.set(i, mutate(networks.get(i)));
		}
		return networks;
	}

	public NeuralNetwork[] mutate(NeuralNetwork... networks) {
		for (int i = 0; i < networks.length; i++) {
			networks[i] = mutate(networks[i]);
		}
		return networks;
	}

	public NeuralNetwork mutate(NeuralNetwork network) {
		Dna dna = new Dna(network);
		dna.biases = DoubleStream.of(dna.biases).map(bias -> getNewBias(bias)).toArray();
		dna.weights = DoubleStream.of(dna.weights).map(weight -> getNewWeight(weight)).toArray();
		network.setBiases(dna.biases);
		network.setWeights(dna.weights);
		return network;
	}

	private double getNewBias(double bias) {
		if (selectedByRandom()) {
			bias = getRandomizedBias();
		}
		return bias;
	}

	private double getNewWeight(double weight) {
		if (selectedByRandom()) {
			weight = getRandomizedWeight();
		}
		return weight;
	}

	private boolean selectedByRandom() {
		return random.nextDouble() < PROBABILITY_OF_MUTATION;
	}

	private double getRandomizedBias() {
		return NeuronParametersGenerator.getRandomBias();
	}

	private double getRandomizedWeight() {
		return NeuronParametersGenerator.getRandomWeight();
	}

}