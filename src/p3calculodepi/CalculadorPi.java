package p3calculodepi;

public class CalculadorPi {
	final static double REFERENCIAPI = 3.14159265358979323846;
	private long numIntervalos;
	private double valorPi;
	private double anchuraIntervalo;
	
	public CalculadorPi(long numIntervalos){
		this.numIntervalos = numIntervalos;
		anchuraIntervalo = 1.0/numIntervalos;
	}

	public double calcularPi(){
		valorPi = 0;
		double x;
		for (long i = 0; i<numIntervalos; i++){
			x= (i+0.5)*anchuraIntervalo;
			valorPi += 4/(1+(x*x));
		}
		valorPi *= anchuraIntervalo;
		return valorPi;
	}
	public static void main (String args[]){
		CalculadorPi programa = new CalculadorPi(1000000000);
		long inicio = System.currentTimeMillis();
		double pi = programa.calcularPi();
		long fin = System.currentTimeMillis();
		System.out.println("valor de pi: "+ pi);
		System.out.println("Error referencia = "+(CalculadorPi.REFERENCIAPI - pi));
		System.out.println("Tiempo empleado : "+(fin-inicio)+" ms");
	}
}
