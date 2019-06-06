package genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;

public class Resultado {

	// Esta clase es una estructura para almacenar los resultados tras una ejecucción.

	// Matriz con los genomas que más puntuación han obtenido tras numGeneraciones para cada uno de los criterios
	// de selección de eliminados (hay 4) y de selección de reproductores (hay 5).
	// El primer array tiene tamaño 4 porque es el número de criterios de selección de eliminados.
	// El segundo array tiene tamaño 5 porque es el número de criterios de selección de reproductores.
	// El tercer array tiene tamáño 6 ya que es el tamaño de un genoma.
	private int [][][] mejoresGenomas = new int [4][5][6];
	// Número de individuos con el mejor genoma con sus criterios
	private int [][] frecMejoresGenomas = new int [4][5];
	// Ejecución a la que pertenecen los resultados
	private int numEjecucion;

	public void setMejorGenoma(Individuo [] poblacion, int criterioElim, int criterioRep) {

		mejoresGenomas [criterioElim-1][criterioRep-1] = poblacion[0].getGenoma();
		// El mejor genoma va a ser el más frecuente. En caso de empate, el que más puntuación tenga.
		/* De momento no va bien.
		int [][] genomas = new int [poblacion.length][6];
		for (int i = 0; i < poblacion.length; i++)
			genomas[i] = poblacion[i].getGenoma();
		Map<int[],Integer> frecuenciaGenomas = new HashMap<int[],Integer>();
		for (int[] genoma : genomas){
			if (!frecuenciaGenomas.containsKey(genoma))
				frecuenciaGenomas.put(genoma, 1);
			else
				frecuenciaGenomas.put(genoma, frecuenciaGenomas.get(genoma) + 1);
		}
		Map.Entry<int[],Integer> maxFrec = null;
		for (Map.Entry<int[], Integer> entrada : frecuenciaGenomas.entrySet())
			if (maxFrec == null || entrada.getValue().compareTo(maxFrec.getValue()) > 0)
				maxFrec = entrada;
			else if (entrada.getValue().compareTo(maxFrec.getValue()) == 0)
				for (int [] genoma : genomas) {
					if (Arrays.equals(genoma,maxFrec.getKey())) break;
					else if (Arrays.equals(genoma, entrada.getKey())) { maxFrec = entrada; break; }
				}
		mejoresGenomas [criterioElim-1][criterioRep-1] = maxFrec.getKey();
		frecMejoresGenomas [criterioElim-1][criterioRep-1] = maxFrec.getValue();
		*/
	}

	public int getNumEjecucion () {
		return this.numEjecucion;
	}

	public void setNumEjecucion(int num) {
		this.numEjecucion = num;
	}

	public void printMejoresGenomas (BufferedWriter writer) {
		String textToFile;
		for (int critElim = 0; critElim < 4; critElim++)
			for (int critRep = 0; critRep < 5; critRep++) {
				textToFile = "\nCritElim " + (critElim + 1) + " y CritReprod " + (critRep + 1) + ". Genoma :" +
						Arrays.toString(mejoresGenomas[critElim][critRep]);// + " en " + frecMejoresGenomas[critElim][critRep] + " individuos";
				try { writer.write(textToFile); } catch (Exception e) {};
				System.out.println(textToFile);
			}
	}
}
