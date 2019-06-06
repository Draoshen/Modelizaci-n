import genetics.Ejecucion;
import genetics.Individuo;
import genetics.Resultado;
import genetics.Utiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {

	private static File output = new File("./out/Resultados.txt");
	private static File output_resumen = new File ("./out/ResultadosResumidos.txt");
	private static BufferedWriter writer;
	private static BufferedWriter writer_resumen;
	private static int tamañoPoblacion = 10;
	private static int numInsercion = (int) (tamañoPoblacion*0.4);
	private static int numGeneraciones = 50;
	private static int numEjecuciones = 10;
	private static Resultado [] resultados = new Resultado [numEjecuciones];

	// todo: seleccion del mejor genoma no va, siempre pone en 1 individuosz
	// todo: mejorar la función de evaluación
	// todo: ir imprimiendo resultados en algún archivo, ir guardando los genomas que más generaciones aguantan

	public static void main(String[] args) throws Exception {

		// Una ejecución del programa consiste en enfrentar a los individuos de la población entre sí
		// durante numGeneraciones
		// Se van a realizar numEjecuciones ejecuciones del programa.
		// Para cada ejecucion, se van a realizar todas las posibles combinaciones de operadores de selección y cruce.

		try {
			writer = new BufferedWriter(new FileWriter(output));
			writer_resumen = new BufferedWriter(new FileWriter(output_resumen));
		} catch (Exception e) {};

		String textToFile = "\n\nINICIO DEL PROGRAMA.\nSe van a realizar " + numEjecuciones
				+ " ejecuciones del programa. En cada ejecución se crea una nueva población.\nCada ejecución consta " +
				"de la evaluación de su población tras " + numGeneraciones + " generaciones para cada uno de los " +
				"criterios de selección.\nHay 4 criterios de selección de individuos para eliminar: 1. Peores, " +
				"2. Padres, 3. Por torneo y 4. Aleatoria.\nHay 5 criterios de selección de individuos para " +
				"reproducirse: 1. Mejores, 2. Peores, 3. Por torneo, 4. Mixta y 5. Aleatoria.\nEn total, en cada " +
				"ejecución se realizan 20 evaluaciones de la población.";
		writer.write(textToFile);
		writer_resumen.write(textToFile);
		System.out.println(textToFile);

		for (int numEjec = 0; numEjec < numEjecuciones; numEjec++) {
			textToFile = "\n\nEJECUCIÓN " + (numEjec+1) + " del programa \n";
			writer.write(textToFile);
			System.out.println(textToFile);

			// INICIALIZACIÓN de la población
			// Array de genomas (array de array de números (genes))
			Individuo[] poblacion = new Individuo[tamañoPoblacion];
			double[][] puntuaciones = new double[tamañoPoblacion][tamañoPoblacion];
			for (int i = 0; i < tamañoPoblacion; i++) {
				poblacion[i] = new Individuo(i);
				for (int j = 0; j < 3; j++)
					poblacion[i].modGen(j, Utiles.getRandomNumberInts(1, 2));
				poblacion[i].modGen(3, Utiles.getRandomNumberInts(0, 2));
				poblacion[i].modGen(4, Utiles.getRandomNumberInts(0, 2));
				while (poblacion[i].getGen(4) == poblacion[i].getGen(3))
					poblacion[i].modGen(4, Utiles.getRandomNumberInts(0, 2));
				poblacion[i].modGen(5, Utiles.getRandomNumberInts(0, 2));
				while (poblacion[i].getGen(5) == poblacion[i].getGen(4)
						|| poblacion[i].getGen(5) == poblacion[i].getGen(3))
					poblacion[i].modGen(5, Utiles.getRandomNumberInts(0, 2));
			}

			// CRITERIO DE PARADA:
			// - Consumo de recursos: alcanzar número de generaciones (HECHO, EN USO).

			// - Superar un umbral de calidad (NO HECHO)

			// - Estancamiento en la mejora (NO HECHO)

			// EJECUCIÓN del programa (numGeneraciones generaciones de partidas entre individuos de la pob)
			Resultado resultado = new Resultado();
			Individuo [] poblacionAux = new Individuo[tamañoPoblacion];
			for (int criterioElim = 1; criterioElim <= 4; criterioElim++)
				for (int criterioRep = 1; criterioRep <= 5; criterioRep++) {
					// Reseteamos la población a como estaba al principio
					for (int i = 0; i < poblacion.length; i++)
						poblacionAux[i] = poblacion[i].clone();
					textToFile = "\n\nEJECUCIÓN "+ (numEjec+1) + " con CRITERIOS: Eliminación: " + criterioElim + " y Reproducción: " + criterioRep;
					writer.write(textToFile);
					System.out.println(textToFile);
					Ejecucion.ejecucionPoblacion(poblacionAux, tamañoPoblacion, numGeneraciones,
							numInsercion, puntuaciones, criterioElim, criterioRep,writer);
					resultado.setMejorGenoma(poblacionAux, criterioElim, criterioRep);
				}
			resultado.setNumEjecucion(numEjec);
			resultados[numEjec] = resultado;
		}
		// Imprimimos el mejor genoma para cada uno de los criterios en cada ejecución
		for (Resultado resultado : resultados) {
			textToFile = "\n\nRESULTADOS DE LA EJECUCIÓN " + (resultado.getNumEjecucion()+1);
			writer.write(textToFile);
			writer_resumen.write(textToFile);
			System.out.println(textToFile);
			resultado.printMejoresGenomas(writer);
			resultado.printMejoresGenomas(writer_resumen);
		}
		writer.close();
		writer_resumen.close();
	}
}



