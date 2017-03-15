package p3calculodepi;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class CalculadorPi {
	final static double REFERENCIAPI = 3.14159265358979323846;
	static double valorPi;
	private static double anchuraIntervalo;

	final static long INTERVALOS = 100000;
	final static int PROCESSORS = 1000;
	final static long INDEX = INTERVALOS / PROCESSORS;
	static ExecutorService pool;


	public CalculadorPi(long numIntervalos) {
		anchuraIntervalo = 1.0 / numIntervalos;
		valorPi = 0.0;
	}

	public static Double calcularPi(long i) {


		double x = (i + 0.5) * anchuraIntervalo;
		return 4 / (1 + (x * x));
	}

	public double inicializarThreads() {

		pool = Executors.newFixedThreadPool(PROCESSORS);
		Set<Future<Double>> set = new HashSet<>();
		for (int i = 0; i < PROCESSORS; i++) {
			Future<Double> future = pool.submit(new PiCalculatorThread(i * INDEX, (i + 1) * INDEX));
			set.add(future);
		}
		for (Future<Double> future : set) {
			try {
				valorPi += future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		pool.shutdown();
		try {
			pool.awaitTermination(20, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return valorPi * anchuraIntervalo;
	}

	public static void main(String args[]) {
		CalculadorPi programa = new CalculadorPi(INTERVALOS);
		long inicio = System.currentTimeMillis();

		double pi = programa.inicializarThreads();
		long fin = System.currentTimeMillis();
		System.out.println("valor de pi: " + pi);
		System.out.println("Error referencia = " + (CalculadorPi.REFERENCIAPI - pi));
		System.out.println("Tiempo empleado : " + (fin - inicio) + " ms");

	}


}