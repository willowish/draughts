package model.genetics;

import model.ai.nn.NeuralNetwork;

public class Configuration {
	// population
	public final static int NUMBER_OF_BEST_NN_TO_RETAIN = 10;
	public final static int NUMBER_OF_TRANSMUTED = 10;
	public final static int NUMBER_OF_RANDOMS_IN_NEW_POPULATION = 10;

	public final static int POPULATION_SIZE = NUMBER_OF_BEST_NN_TO_RETAIN + NUMBER_OF_TRANSMUTED
			+ NUMBER_OF_RANDOMS_IN_NEW_POPULATION;

	// network
	public static final int nodesInInputLayer = NeuralNetwork.nodesInInputLayer;
	public static final int nodesInFirstLayer = NeuralNetwork.nodesInFirstLayer;
	public static final int nodesInSecondLayer = NeuralNetwork.nodesInSecondLayer;
	// node
	public final static double MIN_BIAS = -0.2;
	public final static double MAX_BIAS = 0.2;
	public final static double MIN_WEIGHT = -0.2;
	public final static double MAX_WEIGHT = 0.2;

	// transmutation
	public final static double PROBABILITY_OF_MUTATION = 0.01;

	public static final int weightsCount = nodesInInputLayer * nodesInFirstLayer
			+ nodesInFirstLayer * nodesInSecondLayer + nodesInSecondLayer;
	public static final int biasesCount = nodesInFirstLayer + nodesInSecondLayer + 1;
}
