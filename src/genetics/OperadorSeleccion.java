package genetics;
import java.util.ArrayList;

public class OperadorSeleccion {

	// Recibe una lista ordenada de individuos ya evaluados
	public static void seleccionEliminados (Individuo [] poblacion, int cantidad, int criterio) throws Exception{

		//System.out.println("Criterio de eliminacion: " + criterio);
		switch (criterio) {

			// CRITERIO 1. Eliminar a los n peores (modelo elitista)
			case 1:
				eliminaPeores(poblacion,cantidad);
				break;

			// CRITERIO 2. Eliminar a los n padres (los seleccionados para reproduccion
			case 2:
				eliminaPadres(poblacion,cantidad);
				break;

			// CRITERIO 3. Eliminar por torneo: elegimos dos aleatorios de la población, eliminamos al que tenga peor puntuación
			case 3:
				eliminaPorTorneo(poblacion, cantidad);
				break;

			// CRITERIO 4. Eliminar aleatoriamente
			case 4:
				eliminaAleatori(poblacion, cantidad);
				break;

			default: throw new Exception("Criterio de selección de eliminados no válido");
		}

	}

	public static void seleccionReproductores (Individuo [] poblacion, int cantidad, int criterio) throws Exception {

		//System.out.println("Criterio reproduccion: " + criterio);
		switch (criterio) {

			// CRITERIO 1. Seleccionar como reproductores a los n mejores
			case 1:
				cruzaMejores(poblacion,cantidad);
				break;

			// CRITERIO 2. Seleccionar como reproductores a los n peores
			case 2:
				cruzaPeores(poblacion,cantidad);
				break;

			// CRITERIO 3. Seleccionar por torneo: elegimos dos aleatorios de la población, elegimos al que tenga peor puntuación
			case 3:
				cruzaPorTorneo(poblacion, cantidad);
				break;

			// CRITERIO 4. Seleccionar como reproductores a los n/2 mejores y a los n/2 peores
			case 4:
				cruzaMixto(poblacion, cantidad);
				break;

			// CRITERIO 5. Seleccionar a los reproductores aleatoriamente
			case 5:
				cruzaAleatori(poblacion, cantidad);
				break;

			default: throw new Exception("Criterio de selección de reproductores no válido");
		}
	}
	public static Individuo [] eliminaSeleccionados (Individuo[] poblacion) {
		ArrayList<Individuo> poblacionResultado = new ArrayList<Individuo>();
		for (int i = 0; i < poblacion.length; i++)
			if (!poblacion[i].isParaEliminar()) {
				poblacionResultado.add(poblacion[i]);
			}
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

	private static void cruzaPorTorneo (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			Individuo ind1 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			while (ind1.isParaCruzar()) ind1 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			Individuo ind2 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			while (ind2.isParaCruzar()) ind2 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			if (ind1.getPuntuacion() > ind2.getPuntuacion()) ind1.setParaCruzar(true);
			else ind2.setParaCruzar(true);
		}
	}

	private static void cruzaMixto (Individuo [] poblacion, int cantidad) {
		cruzaMejores(poblacion,cantidad/2);
		cruzaPeores(poblacion,cantidad/2);
	}

	private static void cruzaAleatori (Individuo [] poblacion, int cantidad) {
		int pos;
		for (int i = 0; i < cantidad; i++) {
			pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			while (poblacion[pos].isParaCruzar()) pos = Utiles.getRandomNumberInts(0, (poblacion.length - 1));
			poblacion[pos].setParaCruzar(true);
		}
	}

	private static void eliminaPorTorneo (Individuo [] poblacion, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			Individuo ind1 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			while (ind1.isParaEliminar()) ind1 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			Individuo ind2 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			while (ind2.isParaEliminar()) ind2 = poblacion[Utiles.getRandomNumberInts(0,poblacion.length-1)];
			if (ind1.getPuntuacion() > ind2.getPuntuacion()) ind2.setParaEliminar(true);
			else ind1.setParaEliminar(true);
		}
	}

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
