package model.genetics;

import static model.genetics.Configuration.MAX_BIAS;
import static model.genetics.Configuration.MAX_WEIGHT;
import static model.genetics.Configuration.MIN_BIAS;
import static model.genetics.Configuration.MIN_WEIGHT;

import java.util.Random;

public class NeuronParametersGenerator {

	private final static Random random = new Random();

	public static double getRandomBias() {
		return MIN_BIAS + (MAX_BIAS - MIN_BIAS) * random.nextDouble();
	}

	public static double getRandomWeight() {
		return MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
	}
}
