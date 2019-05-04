import java.util.ArrayList;
import java.util.Random;

public class Mapa {

	private static int dimensionX = 5;

	private static int dimensionY = 5;

	private Casilla [][] posiciones = new Casilla[dimensionX][dimensionY];

	public Mapa(){
		for (int i = 0; i < getDimensionX(); i++)
			for (int j = 0; j < getDimensionY(); j++) {
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

	public Casilla getCasilla(int posicion1, int posicion2){
		Casilla valor = posiciones[posicion1][posicion2];
		return valor;
	}

	public int [] getCoordenadas (Casilla casilla) {
		return casilla.getCoordenadas();
	}

	@Override
	public String toString(){
		String imprimirLinea="";

		String[] imprimirContenido=new String[this.getDimensionX()];

		for (int i = 0; i < imprimirContenido.length; i++) {
			imprimirContenido[i]="Row number= "+i +"---> ";
		}

		for (int i = 0; i < this.getDimensionX(); i++) {
			for (int j = 0; j < this.getDimensionY(); j++) {
				imprimirContenido[i]+="["+posiciones[i][j]+"]";
			}
			imprimirLinea+="[  "+ imprimirContenido[i] +" ] \n";
		}
		return imprimirLinea;
	}

	public int getDimensionY() {
		return this.dimensionY;
	}

	public int getDimensionX() {
		return this.dimensionX;
	}

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

	public ArrayList<Casilla> getAdyacentes (Casilla casillaActual) {
		return getAdyacentes(casillaActual.getCoordenadas());
	}

	public void printMapa () {
		for (int i = 0; i < dimensionY; i++) {
			System.out.print("._____");
		}
		System.out.print(".\n");
		for (int i = 0; i < dimensionX; i++) {
			for (int j = 0; j < dimensionY; j++)
				System.out.print(".__" + String.valueOf(posiciones[i][j].getPais().getId()) + "__");
			System.out.print(".\n");
		}
	}

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}


}
