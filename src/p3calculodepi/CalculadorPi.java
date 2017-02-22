package p3calculodepi;

import java.util.concurrent.*;

public class CalculadorPi {
	final static double REFERENCIAPI = 3.14159265358979323846264338327950288419716939937510582097494459230781640628;
	final static long INTERVALOS = 10000000000L;
	final static long TASKS = 10000;
	final static Long INDEX = INTERVALOS / TASKS;

	private long numIntervalos;
	private double valorPi;
	public static double anchuraIntervalo;
	static ExecutorService executor;
	private static CompletionService<Double> completionService;

	public CalculadorPi(long numIntervalos) {
		this.numIntervalos = numIntervalos;
		anchuraIntervalo = 1.0 / numIntervalos;
	}


	public static double calcularPartePi(Long i) {
		double x = (i + 0.5) * anchuraIntervalo;
		return 4 / (1 + (x * x));
	}

	public double inicializarThreads() {
		PiCalculatorThread thread;
		valorPi = 0.0;
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		completionService = new ExecutorCompletionService<Double>(executor);
		for (long i = 0; i < TASKS; i++) {
			completionService.submit(new PiCalculatorThread(INDEX * i, INDEX * (i + 1)));
		}
		for (long i = 0; i < TASKS; i++) {
			Double valor;
			Future<Double> future;
			try {
				future = completionService.take();
				if ((valor = future.get()) != null) {
					valorPi += valor;
				}

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}
		executor.shutdown();
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
