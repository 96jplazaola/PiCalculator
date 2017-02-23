package p3calculodepi;

public class CalculadorPi {
	final static double REFERENCIAPI = 3.14159265358979323846;
	static double valorPi;
	private static double anchuraIntervalo;

	final static long INTERVALOS = 10000000000L;
	final static int PROCESSORS = Runtime.getRuntime().availableProcessors();
	final static long INDEX = INTERVALOS / PROCESSORS;


	public CalculadorPi(long numIntervalos) {
		anchuraIntervalo = 1.0 / numIntervalos;
		valorPi = 0.0;
	}

	public static Double calcularPi(long i) {


		double x = (i + 0.5) * anchuraIntervalo;
		return 4 / (1 + (x * x));
	}

	public double inicializarThreads() {
		Thread[] threads = new Thread[PROCESSORS];
		for (int i = 0; i < PROCESSORS; i++) {
			threads[i] = new Thread(new PiCalculatorThread(i * INDEX, (i + 1) * INDEX));
			threads[i].start();
		}
		try {
			for (int i = 0; i < PROCESSORS; i++) {
				threads[i].join();
			}

		} catch (InterruptedException e) {

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

	public static synchronized void addValuePi(double valorPi) {
		CalculadorPi.valorPi += valorPi;
	}

}