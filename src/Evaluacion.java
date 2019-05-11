import game.Pais;

public class Evaluacion {

	public static int evaluar (Pais pais) {
		//Prototipo, su puntuación es el número de casillas que tiene
		return pais.getTerritorio().size();
	}

}
