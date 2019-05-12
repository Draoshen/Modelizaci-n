import game.Pais;

public class Evaluacion {

	// TODO: pensar en cómo evaluar a un país tras una partida

	public static int evaluar (Pais pais) {
		//Prototipo, su puntuación es el número de casillas que tiene
		return pais.getTerritorio().size();
	}

}
