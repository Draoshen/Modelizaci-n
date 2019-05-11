import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OperadorCruce {

	public static Individuo[] generarNuevosIndividuos (Individuo[] poblacion, int cantidad) throws Exception {
		ArrayList<Individuo> reproductores = new ArrayList<>();
		ArrayList<Individuo> repAux;
		ArrayList <Individuo> nuevos = new ArrayList<Individuo>();
		int numGenerados = 0;

		for (int i = 0; i < poblacion.length; i++)
			if (poblacion[i].isParaCruzar()) reproductores.add(poblacion[i]);

		if (reproductores.size() <= 1) throw new Exception("REPRODUCTORES INSUFICIENTES: Se necesitan al menos 2.");

		repAux = reproductores;
		while (numGenerados < cantidad) {

			// Si ya se han reproducido todos, permitimos repeticiones de reproductores
			if (repAux.size() < 2) repAux = reproductores;
			int prog1 = Utiles.getRandomNumberInts(0,repAux.size());
			int prog2 = Utiles.getRandomNumberInts(0,repAux.size());
			while (prog1 == prog2) prog2 = Utiles.getRandomNumberInts(0,repAux.size());
			nuevos.add(cruzar(repAux.get(prog1),repAux.get(prog2)));
			repAux.remove(prog1);
			repAux.remove(prog2);
			numGenerados++;
		}
		if (nuevos.size() != cantidad) throw new Exception("ERROR. No se ha generado la cantidad esperada de individuos ("+cantidad+").");

		return nuevos.toArray(new Individuo[nuevos.size()]);
	}

	public static Individuo [] insertarNuevos (Individuo [] poblacion, Individuo [] nuevos, int tamPoblacion) throws Exception {

		if (nuevos.length + poblacion.length != tamPoblacion)
			throw new Exception("ERROR. Num individuos eliminados != Num individuos a insertar");

		ArrayList<Individuo> nuevaPoblacion = new ArrayList<Individuo>(Arrays.asList(poblacion));
		nuevaPoblacion.addAll(Arrays.asList(nuevos));
		return nuevaPoblacion.toArray(new Individuo[nuevaPoblacion.size()]);
	}

	private static Individuo cruzar (Individuo progenitor1, Individuo progenitor2) {
		Individuo nuevoIndividuo = new Individuo();
		Individuo [] progenitores = new Individuo[]{progenitor1,progenitor2};

		for (int i = 0; i < progenitor1.getGenoma().length; i++) {
			nuevoIndividuo.modGen(i, progenitores[Utiles.getRandomNumberInts(0,1)].getGen(i));
		}
		return nuevoIndividuo;
	}

}
