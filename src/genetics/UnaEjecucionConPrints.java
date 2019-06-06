package genetics;

import game.Mapa;
import game.Pais;
import game.RealizarPartida;

import java.util.Arrays;

public class UnaEjecucionConPrints {

	static int ultimoID = 10;
	static int tamañoPoblacion = 10;
	static int numInsercion = 4;
	static int criterioElim = 1;
	static int criterioRep = 1;

	// todo: terminar operadores de selección
	// todo: equilibrar estrategias del juego, que no se queden todos los turno sin hacer nada
	// todo: mejorar la función de evaluación
	// todo: ir imprimiendo resultados en algún archivo, ir guardando los genomas que más generaciones aguantan

	public static void main(String[] args) throws Exception {

		// INICIALIZACIÓN
		// Array de genomas (array de array de números (genes))

		Individuo[] poblacion = new Individuo[tamañoPoblacion];
		double [][] puntuaciones = new double [tamañoPoblacion][tamañoPoblacion];

		for (int i = 0; i < tamañoPoblacion; i++) {
			poblacion[i] = new Individuo(i);
			for (int j = 0; j < 3; j++)
				poblacion[i].modGen(j, Utiles.getRandomNumberInts(1, 2));
			poblacion[i].modGen(3, Utiles.getRandomNumberInts(0,2));
			poblacion[i].modGen(4, Utiles.getRandomNumberInts(0,2));
			while (poblacion[i].getGen(4) == poblacion[i].getGen(3)) poblacion[i].modGen(4, Utiles.getRandomNumberInts(0,2));
			poblacion[i].modGen(5, Utiles.getRandomNumberInts(0,2));
			while (poblacion[i].getGen(5) == poblacion[i].getGen(4)
					|| poblacion[i].getGen(5) == poblacion[i].getGen(3))
				poblacion[i].modGen(5, Utiles.getRandomNumberInts(0,2));
			/*
			for (int j = 3; j < 6; j++)
				poblacion[i].modGen(j, genetics.Utiles.getRandomNumberInts(0, 2));
				*/
		}

		// CRITERIO DE PARADA:
		// - Consumo de recursos: alcanzar número de generaciones
		int numGeneraciones = 50;

		// - Superar un umbral de calidad

		// - Estancamiento en la mejora

		for (int generacion = 0; generacion < numGeneraciones; generacion++) {

			System.out.println("\nGENERACIÓN: " + (generacion+1));
			Mapa mapa = new Mapa(5, 5);

			// EVALUACIÓN: Enfrentamos todos contra todos y los evaluamos
			for (int i = 0; i < tamañoPoblacion; i++) {
				//System.out.println("País actual: " + i);
				for (int j = (i + 1); j < tamañoPoblacion; j++) {
					if (i != j) {
						//System.out.println("i , j : " + i + " " + j);
						mapa = new Mapa(5,5);
						Pais paisI = new Pais("Pais " + (i + 1), (i + 1), mapa, poblacion[i].getGenoma());
						Pais paisJ = new Pais("Pais " + (j + 1), (j + 1), mapa, poblacion[j].getGenoma());
						RealizarPartida.realizarPartida(mapa, paisI, paisJ);
						if (paisI.getTerritorio().size() > paisJ.getTerritorio().size()) {
							paisI.setGanador(true);
							paisJ.setGanador(false);
						} else if (paisI.getTerritorio().size() == paisJ.getTerritorio().size()) {
							paisI.setGanador(false);
							paisJ.setGanador(false);
						}
						else {
							paisI.setGanador(false);
							paisJ.setGanador(true);
						}
						puntuaciones[i][j] = Evaluacion.evaluar(paisI);
						//System.out.println("pais puntuado "+ puntuaciones[i][j]);
						puntuaciones[j][i] = Evaluacion.evaluar(paisJ);
					} else {
						puntuaciones[i][j] = -1;
					}
				}
				poblacion[i].setPuntuacion(Utiles.calcularMedia(puntuaciones[i]));
				//System.out.println("puntuación del país " + i + ": " + poblacion[i].getPuntuacion());
			}
			Arrays.toString(puntuaciones);
			Arrays.sort(poblacion, new Utiles());
			System.out.println("Individuos ordenados POST evaluación");
			for (Individuo individuo : poblacion)
				System.out.println(individuo.toString());
			// Operador de selección: marcamos a los que se van a eliminar, sus posiciones serán
			// sustituidas por los nuevos individuos resultados de los cruces.
			OperadorSeleccion.seleccionReproductores(poblacion,numInsercion,criterioRep);
			OperadorSeleccion.seleccionEliminados(poblacion,numInsercion,criterioElim);
			for (Individuo individuo : poblacion)
				if (individuo.isParaEliminar()) System.out.println("Eliminados: genetics.Individuo " + individuo.getId());
			Individuo[] nuevosIndividuos = OperadorCruce.generarNuevosIndividuos(poblacion,numInsercion);
			Individuo[] poblacionReducida = OperadorSeleccion.eliminaSeleccionados(poblacion);
			poblacion = OperadorCruce.insertarNuevos(poblacionReducida, nuevosIndividuos,numInsercion);
			for (Individuo ind : nuevosIndividuos)
				System.out.println("Nuevo generado: genetics.Individuo " + ind.getId());

			for (Individuo individuo : poblacion) {
				individuo.setParaCruzar(false);
				individuo.setParaEliminar(false);
			}
		}
	}

}



