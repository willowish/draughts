package model.genetics.mutation;

import static model.genetics.Configuration.*;

import java.util.Random;

public class NeuronParametersGenerator {

	private final static Random random = new Random();

	static double getRandomBias() {
		return MIN_BIAS + (MAX_BIAS - MIN_BIAS) * random.nextDouble();
	}

	static double getRandomWeight() {
		return MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
	}
}
