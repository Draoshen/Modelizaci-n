import java.util.ArrayList;
import java.util.Arrays;

public class OperadorSeleccion {

	// Recibe una lista ordenada de individuos ya evaluados
	public static void seleccionEliminados (Individuo [] poblacion, int cantidad) {

		// Eliminar a los n peores (modelo elitista)
		eliminaPeores(poblacion,cantidad);

		// Eliminar a los n padres (los seleccionados para reproduccion
		// eliminaPadres(poblacion,cantidad);

		// Eliminar aleatoriamente
		// eliminaAleatori(poblacion, cantidad);
	}

	public static void seleccionReproductores (Individuo [] poblacion, int cantidad) {

		// Seleccionar como reproductores a los n mejores
		 cruzaMejores(poblacion,cantidad);

		// Seleccionar como reproductores a los n peores
		// cruzaPeores(poblacion,cantidad);

		// Seleccionar como reproductores a los n/2 mejores y a los n/2 peores
		// cruzaMixto(poblacion,cantidad);

		// Seleccionar a los reproductores aleatoriamente
		// cruzaAleatori(poblacion, cantidad);
	}
	public static Individuo [] eliminaSeleccionados (Individuo[] poblacion) {
		ArrayList<Individuo> poblacionResultado = new ArrayList<Individuo>();

		for (int i = 0; i < poblacion.length; i++)
			if (!poblacion[i].isParaEliminar()) poblacionResultado.add(poblacion[i]);

		return poblacionResultado.toArray(new Individuo[poblacionResultado.size()]);
	}

	private static void cruzaMejores (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			poblacion[i].setParaCruzar();
	}

	private static void cruzaPeores (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			poblacion[poblacion.length-i].setParaCruzar();
	}

	private static void cruzaMixto (Individuo [] poblacion, int cantidad) {
		cruzaMejores(poblacion,cantidad/2);
		cruzaPeores(poblacion,cantidad/2);
	}

	private static void cruzaAleatori (Individuo [] poblacion, int cantidad) {
		int pos;
		for (int i = 0; i < cantidad; i++) {
			pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			if (!poblacion[pos].isParaCruzar()) poblacion[pos].setParaCruzar();
		}
	}

	private static void eliminaPeores (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			poblacion[poblacion.length-i].setParaEliminar();
	}

	private static void eliminaPadres(Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			if (poblacion[poblacion.length-i].isParaCruzar())
				poblacion[poblacion.length-i].setParaEliminar();
	}

	private static void eliminaAleatori (Individuo [] poblacion, int cantidad) {
		int pos;
		for (int i = 0; i < cantidad; i++) {
			pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			if (!poblacion[pos].isParaEliminar()) poblacion[pos].setParaEliminar();
		}
	}

}
