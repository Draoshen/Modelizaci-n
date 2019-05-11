package gameConPrints;

import java.util.Random;

public class RealizarPartida {

	// TODO: replantear condiciones para las acciones. Con el genoma x,x,x,2,1,3 no hacen nada, por ejemplo. A veces detecta Mover Población pero no lo hace o va mal

	// TODO: parametrizar. game.RealizarPartida ya no será el main, sino que realizará una partida con un mapa y genomas de países dados.
	// ARGUMENTOS: game.Mapa del juego, ArrayList de genomas de países que participarán, [opcional] condiciones iniciales (casillas iniciales,población, productividad, etc)
	// RETURN: game.Mapa final, Genoma del ganador, Genoma del (de los) perdedor (perdedores), [opcional] estado final de cada país (población, comida, etc).
	// Con ésto, tendremos una función de evaluación que valorará:
	// - la relación casillas/población/comida y le asignará una nota al país.
	// - si ha ganado o ha perdido.
	// Con ésto le dará una posición en el ranking de todos los países de la población que se han enfrentado
	// De ahí ya al proceso de selección y reproducción, y vuelta a empezar.

	public static void main (String [] args) {


		Mapa mapa = new Mapa(5,5);
		int [] genomaPais1 = new int [] {1, 1, 1, 0 , 1 , 2};
		int [] genomaPais2 = new int [] {1, 1, 1, 0 , 1 , 2};
		Pais pais1 = new Pais("Pais 1",1,mapa,genomaPais1);
		Pais pais2 = new Pais("Pais 2", 2,mapa,genomaPais2);

		// GENERACIÓN DE LOS PAÍSES cada uno con su casilla inicial.

		Casilla casillaInicial = mapa.getCasilla(getRandomNumberInts(0,mapa.getDimensionX()-1),getRandomNumberInts(0,mapa.getDimensionY()-1));
		while (casillaInicial.getPais()!= null)
			casillaInicial = mapa.getCasilla(getRandomNumberInts(0,mapa.getDimensionX()-1),getRandomNumberInts(0,mapa.getDimensionY()-1));
		casillaInicial.addPobCivil(333);
		casillaInicial.addPobMilitar(167);
		casillaInicial.setPais(pais1);
		pais1.addTerritorio(casillaInicial);
		System.out.println(pais1);
		pais1.printCasillas();
		casillaInicial = mapa.getCasilla(getRandomNumberInts(0,mapa.getDimensionX()-1),getRandomNumberInts(0,mapa.getDimensionY()-1));
		while (casillaInicial.getPais()!= null)
			casillaInicial = mapa.getCasilla(getRandomNumberInts(0,mapa.getDimensionX()-1),getRandomNumberInts(0,mapa.getDimensionY()-1));
		casillaInicial.addPobCivil(333);
		casillaInicial.addPobMilitar(167);
		casillaInicial.setPais(pais2);
		pais2.addTerritorio(casillaInicial);
		System.out.println(pais2);
		pais2.printCasillas();

		// Prueba fin de partida cuando se llegue al turno numTurnos:
		int numTurnos = 50;
		int turno = 1;
		//while(turno <= numTurnos){

		// Prueba fin de partida hasta que gane uno:
		while (pais1.getTerritorio().size() >0 && pais2.getTerritorio().size() >0 && turno <= numTurnos) {
			pais1.realizarTurnoPais();
			pais1.terminaTurno();
			mapa.printMapa();
			pais2.realizarTurnoPais();
			pais2.terminaTurno();
			mapa.printMapa();
			turno++;
		}
		System.out.println("\n\nFIN PARTIDA\n");

	}

	// TODO : BUSCAR INTERFACE JAVA
	// TODO : BUSCAR VARIAS SI HAY EXCEPCIONES A LA VEZ, ENTONCES PONER VARIOS CATCH'S

	public static int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}


}











