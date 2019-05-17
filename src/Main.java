import java.util.Arrays;
import java.util.Comparator;
import game.*;

public class Main {

	static int ultimoID = 10;
	static int tamañoPoblacion = 10;
	static int numInsercion = 4;


	public static void main(String[] args) throws Exception {

		// INICIALIZACIÓN
		// Array de genomas (array de array de números (genes))

		Individuo[] poblacion = new Individuo[tamañoPoblacion];
		int[][] puntuaciones = new int[tamañoPoblacion][tamañoPoblacion];

		for (int i = 0; i < tamañoPoblacion; i++) {
			poblacion[i] = new Individuo(i);
			for (int j = 0; j < 3; j++)
				poblacion[i].modGen(j, Utiles.getRandomNumberInts(1, 2));
			for (int j = 3; j < 6; j++)
				poblacion[i].modGen(j, Utiles.getRandomNumberInts(0, 2));
		}

		/* Prueba individual
		Mapa mapa = new Mapa(5,5);
		System.out.println("\nCREACIÓN DE LOS PAÍSES.");
		int [] genomaPais1 = new int [] {1, 1, 1, 0 , 1 , 2};
		int [] genomaPais2 = new int [] {1, 1, 1, 0 , 1 , 2};
		Pais pais1 = new Pais("Pais 1",1,mapa,genomaPais1);
		Pais pais2 = new Pais("Pais 2", 2,mapa,genomaPais2);
		RealizarPartida.realizarPartida(mapa,pais1,pais2);
		*/

		// CRITERIO DE PARADA:
		// - Consumo de recursos: alcanzar número de generaciones
		int numGeneraciones = 10;

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
						game.RealizarPartida.realizarPartida(mapa, paisI, paisJ);
						puntuaciones[i][j] = Evaluacion.evaluar(paisI);
						//System.out.println("pais puntuado "+ puntuaciones[i][j]);
						puntuaciones[j][i] = Evaluacion.evaluar(paisJ);
					} else {
						puntuaciones[i][j] = -1;
					}
				}
				int [] p = new int [] {2,4,6};
				poblacion[i].setPuntuacion(Utiles.calcularMedia(puntuaciones[i]));
				//System.out.println("puntuación del país " + i + ": " + poblacion[i].getPuntuacion());
			}
			Arrays.toString(puntuaciones);
			Arrays.sort(poblacion, new compararIndividuos());
			System.out.println("Individuos ordenados POST evaluación");
			for (Individuo individuo : poblacion)
				System.out.println(individuo.toString());
			// Operador de selección: marcamos a los que se van a eliminar, sus posiciones serán
			// sustituidas por los nuevos individuos resultados de los cruces.
			OperadorSeleccion.seleccionReproductores(poblacion);
			OperadorSeleccion.seleccionEliminados(poblacion);
			Individuo[] nuevosIndividuos = OperadorCruce.generarNuevosIndividuos(poblacion);
			Individuo[] poblacionReducida = OperadorSeleccion.eliminaSeleccionados(poblacion);
			poblacion = OperadorCruce.insertarNuevos(poblacionReducida, nuevosIndividuos);


			for (Individuo ind : nuevosIndividuos)
				System.out.println("Nuevo generado: Individuo " + ind.getId());

			for (Individuo individuo : poblacion) {
				individuo.setParaCruzar(false);
				individuo.setParaEliminar(false);

			}
		}
	}

}

class compararIndividuos implements Comparator<Individuo> {
	@Override
	public int compare(Individuo ind1, Individuo ind2) {
		return ind2.getPuntuacion() - ind1.getPuntuacion();
	}
}

