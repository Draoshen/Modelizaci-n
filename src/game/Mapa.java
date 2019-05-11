package game;

import java.util.ArrayList;
import java.util.Random;

public class Mapa {

	private int dimensionX;

	private int dimensionY;

	private Casilla [][] posiciones;

	public Mapa(int dimensionX, int dimensionY){
		this.posiciones = new Casilla[dimensionX][dimensionY];
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		for (int i = 0; i < dimensionX; i++)
			for (int j = 0; j < dimensionY; j++) {
				int pobMax = getRandomNumberInts(500,5000);
				int comidaMax = getRandomNumberInts(pobMax,pobMax*2);
				int comida = getRandomNumberInts(pobMax,comidaMax);
				int productividad = getRandomNumberInts(2,4);
				int [] coordenadas = {i,j};
				posiciones[i][j] = new Casilla(comida, comidaMax, productividad, pobMax,null, coordenadas, null);
			}
		for (int i = 0; i < getDimensionX(); i++)
			for (int j = 0; j < getDimensionY(); j++)
				posiciones[i][j].setAdyacentes(this.getAdyacentes(posiciones[i][j]));
	}

	public Casilla getCasilla(int posicion1, int posicion2){ return posiciones[posicion1][posicion2]; }

	public int getDimensionY() { return this.dimensionY; }

	public int getDimensionX() { return this.dimensionX; }

	public ArrayList<Casilla> getAdyacentes (int [] coordenadas) {
		int x = coordenadas[0];
		int y = coordenadas[1];
		ArrayList<Casilla> adyacentes = new ArrayList<Casilla>();

		for (int i = 0; i < this.getDimensionX(); i++)
			for (int j = 0; j < this.getDimensionY(); j++) {
				if (((i == x) && ((j == y+1) || (j == y-1)))
						|| ((j == y) && ((i == x+1) || (i == x-1)))) {
					adyacentes.add(this.posiciones[i][j]);
				}
			}
		return adyacentes;
	}

	public ArrayList<Casilla> getAdyacentes (Casilla casillaActual) { return getAdyacentes(casillaActual.getCoordenadas()); }

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}

	/* todo quizas borrar, no se usa
	public int [] getCoordenadas (game.Casilla casilla) {
		return casilla.getCoordenadas();
	}
	*/

	public void printMapa () {
		System.out.println("MAPA: ");
		for (int i = 0; i < dimensionY; i++) {
			System.out.print("._____");
		}
		System.out.print(".\n");
		for (int i = 0; i < dimensionX; i++) {
			for (int j = 0; j < dimensionY; j++) {
				if (posiciones[i][j].getPais() != null)
					System.out.print(".__" + String.valueOf(posiciones[i][j].getPais().getId()) + "__");
				else
					System.out.print("._____");
			}
			System.out.print(".\n");
		}
	}

	/* todo no lo usamos, quizas borrar
	@Override
	public String toString(){
		String imprimirLinea="";

		String[] imprimirContenido=new String[this.getDimensionX()];

		for (int i = 0; i < imprimirContenido.length; i++) {
			imprimirContenido[i]="Row number= "+i +"---> ";
		}

		for (int i = 0; i < this.getDimensionX(); i++) {
			for (int j = 0; j < this.getDimensionY(); j++) {
				imprimirContenido[i]+="["+posiciones[i][j].toString()+"]";
			}
			imprimirLinea+="[  "+ imprimirContenido[i] +" ] \n";
		}
		return imprimirLinea;
	}
	*/
}
