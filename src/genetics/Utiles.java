package genetics;

import genetics.Individuo;

import  java.util.Random;
import  java.util.Comparator;

public class Utiles implements Comparator<Individuo> {

	public static int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}

	public static int calcularMedia(double [] numeros) {
		int sum = 0;
		int count = 0;
		for (double num : numeros) {
			if (num >= 0) {
				sum += num;
				count++;
			}
		}
		return (count > 0) ? sum / count : 0;
	}

	@Override
	public int compare(Individuo ind1, Individuo ind2) {
		return ind2.getPuntuacion() - ind1.getPuntuacion();
	}
}
