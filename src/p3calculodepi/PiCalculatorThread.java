package p3calculodepi;

import java.util.concurrent.Callable;

public class PiCalculatorThread implements Callable<Double> {

	private long indexStart;
	private long indexEnd;
	private double valorPi;

	public PiCalculatorThread(long l, long m) {
		this.indexStart = l;
		this.indexEnd = m;
		this.valorPi = 0.0;

	}

	@Override
	public Double call() {
		double fragmentoPi = 0.0;
		for (long j = indexStart; j < indexEnd; j++) {
			fragmentoPi = CalculadorPi.calcularPi(j);

			this.valorPi += fragmentoPi;

		}
		return valorPi;
	}


}