import java.util.ArrayList;
import java.util.Arrays;

public class OperadorSeleccion {

	// Recibe una lista ordenada de individuos ya evaluados
	public static void seleccionEliminados (Individuo [] poblacion) {
		int cantidad = Main.numInsercion;
		// Eliminar a los n peores (modelo elitista)
		// eliminaPeores(poblacion,cantidad);

		// Eliminar a los n padres (los seleccionados para reproduccion
		// eliminaPadres(poblacion,cantidad);

		// Eliminar por torneo: elegimos dos aleatorios de la población, eliminamos al que tenga peor puntuación
		// eliminaPorTorneo(poblacion, cantidad);

		// Eliminar aleatoriamente
		 eliminaAleatori(poblacion, cantidad);

		for (Individuo individuo : poblacion)
			if (individuo.isParaEliminar()) System.out.println("Eliminados: Individuo " + individuo.getId());
	}

	public static void seleccionReproductores (Individuo [] poblacion) {
		int cantidad = Main.numInsercion;
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
			poblacion[i].setParaCruzar(true);
	}

	private static void cruzaPeores (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			poblacion[poblacion.length-(i+1)].setParaCruzar(true);
	}

	private static void cruzaMixto (Individuo [] poblacion, int cantidad) {
		cruzaMejores(poblacion,cantidad/2);
		cruzaPeores(poblacion,cantidad/2);
	}

	private static void cruzaAleatori (Individuo [] poblacion, int cantidad) {
		int pos;
		for (int i = 0; i < cantidad; i++) {
			pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			if (!poblacion[pos].isParaCruzar()) poblacion[pos].setParaCruzar(true);
		}
	}

	/* todo: todavía por terminar
	private static void eliminaPorTorneo (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			Individuo ind1 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			Individuo ind2 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			compararIndividuos

		}
	}
	*/

	private static void eliminaPeores (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++)
			poblacion[poblacion.length-(i+1)].setParaEliminar(true);
	}

	private static void eliminaPadres(Individuo [] poblacion, int cantidad) {
		for (Individuo individuo : poblacion)
			if (individuo.isParaCruzar())
				individuo.setParaEliminar(true);
	}

	private static void eliminaAleatori (Individuo [] poblacion, int cantidad) {
		int pos;
		int marcados = 0;
		while (marcados < cantidad) {
			pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			if (!poblacion[pos].isParaEliminar()) {
				poblacion[pos].setParaEliminar(true);
				marcados++;
			}
		}
	}

}
