package p3calculodepi;

public class PiCalculatorThread implements Runnable {

	private long indexStart;
	private long indexEnd;
	private double  valorPi;

	public PiCalculatorThread(long l, long m) {
		this.indexStart = l;
		this.indexEnd = m;
		this.valorPi = 0.0;

	}

	@Override
	public void run() {
		double fragmentoPi = 0.0;
		for (long j = indexStart; j < indexEnd; j++) {
			fragmentoPi = CalculadorPi.calcularPi(j);

			this.valorPi += fragmentoPi;

		}
		CalculadorPi.addValuePi(valorPi);
	}



}