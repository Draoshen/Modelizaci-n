package genetics;

import game.*;
import java.io.BufferedWriter;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.Arrays;

public class Ejecucion {

	// Variable para saber cuál es el ID del último individuo creado. Va creciendo conforme se crean individuos.
	static int ultimoID = 10;
	public static void ejecucionPoblacion
			(Individuo [] poblacion, int tamañoPoblacion, int numGeneraciones, int numInsercion,
			 double [][] puntuaciones, int criterioElim, int criterioRep, BufferedWriter writer)
			throws Exception {

		for (int generacion = 0; generacion < numGeneraciones; generacion++) {

			//System.out.println("\nGENERACIÓN: " + (generacion + 1));
			Mapa mapa = new Mapa(5, 5);

			// EVALUACIÓN: Enfrentamos todos contra todos y los evaluamos
			for (int i = 0; i < tamañoPoblacion; i++) {
				for (int j = (i + 1); j < tamañoPoblacion; j++) {
					if (i != j) {
						mapa = new Mapa(5, 5);
						Pais paisI = new Pais("Pais " + (i + 1), (i + 1), mapa, poblacion[i].getGenoma());
						Pais paisJ = new Pais("Pais " + (j + 1), (j + 1), mapa, poblacion[j].getGenoma());
						game.RealizarPartida.realizarPartida(mapa, paisI, paisJ);
						if (paisI.getTerritorio().size() > paisJ.getTerritorio().size()) {
							paisI.setGanador(true);
							paisJ.setGanador(false);
						} else if (paisI.getTerritorio().size() == paisJ.getTerritorio().size()) {
							paisI.setGanador(false);
							paisJ.setGanador(false);
						} else {
							paisI.setGanador(false);
							paisJ.setGanador(true);
						}
						puntuaciones[i][j] = Evaluacion.evaluar(paisI);
						puntuaciones[j][i] = Evaluacion.evaluar(paisJ);
					} else {
						puntuaciones[i][j] = -1;
					}
				}
				poblacion[i].setPuntuacion(Utiles.calcularMedia(puntuaciones[i]));
			}
			//Arrays.toString(puntuaciones);
			Arrays.sort(poblacion, new Utiles());

			if (generacion < (numGeneraciones-1)) {
				// Operador de selección: marcamos a los que se van a eliminar, sus posiciones serán
				// sustituidas por los nuevos individuos resultados de los cruces.
				OperadorSeleccion.seleccionReproductores(poblacion, numInsercion, criterioRep);
				OperadorSeleccion.seleccionEliminados(poblacion, numInsercion, criterioElim);
				Individuo[] nuevosIndividuos = OperadorCruce.generarNuevosIndividuos(poblacion, numInsercion);
				Individuo[] poblacionReducida = OperadorSeleccion.eliminaSeleccionados(poblacion);
				poblacion = OperadorCruce.insertarNuevos(poblacionReducida, nuevosIndividuos, tamañoPoblacion);
			}
			//for (genetics.Individuo ind : nuevosIndividuos)
			//	System.out.println("Nuevo generado: genetics.Individuo " + ind.getId());

			for (Individuo individuo : poblacion) {
				individuo.setParaCruzar(false);
				individuo.setParaEliminar(false);
			}
		}
		String textToFile = "\nIndividuos de la población tras " + numGeneraciones + " generaciones:";
		writer.write(textToFile);
		System.out.println(textToFile);
		for (Individuo individuo : poblacion) {
			textToFile = "\n"+ individuo.toString();
			writer.write(textToFile);
			System.out.println(textToFile);
		}

		// Al final de la ejecución, reiniciamos la variable ultimoID para la siguiente ejecución.
		ultimoID = 10;

		/*
		PARA PRUEBAS. Comprobar cuáles se reproducen y cuáles se eliminan.
		for (genetics.Individuo individuo : poblacion)
			if (individuo.isParaCruzar())
				System.out.println("Reproductores: " + individuo.toString());
		for (Individuo individuo : poblacion)
			if (individuo.isParaEliminar())
				System.out.println("Eliminados: " + individuo.toString());
				*/
	}
}
