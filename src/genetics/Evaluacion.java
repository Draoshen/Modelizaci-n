package genetics;

import game.Casilla;
import game.Pais;

public class Evaluacion {

	public static double evaluar (Pais pais) {

		//return evaluaNumCasillas(pais);

		//return evaluaRelacionPobComida(pais);

		//return evaluaNumMilitares(pais);

		return evaluaGanadoresNumCasillas(pais);

	}

	// EVALUACION 1
	// Prototipo, su puntuación es el número de casillas que tiene
	// return pais.getTerritorio().size();
	private static double evaluaNumCasillas (Pais pais) {
		return pais.getTerritorio().size();
	}

	/*
		// EVALUACION 2
		// Otra forma de evaluar: la puntuación es la media de la puntuación de cada casilla.
		// La puntuación de una casilla es una relación entre el índice de la población máxima, la población total y
		// la comida que hay.
		// Con ésto se premia más al que más gente tenga en sus casillas.
		*/
	private static double evaluaRelacionPobComida (Pais pais) {
		double puntuacion = 0;
		if (pais.getTerritorio().size() <= 0) return 0;
		for (Casilla casilla : pais.getTerritorio())
			puntuacion += ((casilla.getPobTotal() / (double) casilla.getPobMax()) * 100 + (casilla.getComida() / (double)casilla.getComidaMax()) * 100) / 2;

		puntuacion = puntuacion/pais.getTerritorio().size();
		// System.out.println("La puntuacion es: " + puntuacion);
		// Return
		return puntuacion;
	}

	/*
		// EVALUACION 3: se puntúa más cuantos más militares se tiene.
		// Otra forma de evaluar: la puntuación es la media de la puntuación de cada casilla.
		// La puntuación de una casilla viene dada por el número de militares que tiene.
		*/
	private static double evaluaNumMilitares (Pais pais) {
		double puntuacion = 0;
		if (pais.getTerritorio().size() <= 0) return 0;
		for (Casilla casilla : pais.getTerritorio())
			puntuacion += casilla.getPobMilitar();
		puntuacion = puntuacion/pais.getTerritorio().size();
		// System.out.println("La puntuacion es: " + puntuacion);
		// Return
		return puntuacion;
	}

	// EVALUACION 4: se separa ganadores y perdedores, y además se puntúa más cuantas más casillas se tiene.
	// Si ha ganado la partida, le damos una puntuación extra para separarlo de los que no han ganado.

	private static double evaluaGanadoresNumCasillas (Pais pais) {
		double puntuacion = 0;
		if (pais.isGanador())
			puntuacion += 1000;

		puntuacion += pais.getTerritorio().size();
		return puntuacion;
	}

}
