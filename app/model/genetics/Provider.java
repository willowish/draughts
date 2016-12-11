package model.genetics;

public interface Provider<T> {
	/**
	 * this method should return new empty object which implements Network
	 */
	T get();
}
