package p3calculodepi;

import java.util.concurrent.Callable;

/**
 * Created by joanes on 2/21/17.
 */
public class PiCalculatorThread implements Callable<Double> {

	private Long startIndex;
	private Long endIndex;

	public PiCalculatorThread(Long startIndex, Long endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	public Double call() {
		Double pi = 0.0;
		for (long i = startIndex; i < endIndex; i++) {
			pi += CalculadorPi.calcularPartePi(i);
		}
		return pi;
	}
}
