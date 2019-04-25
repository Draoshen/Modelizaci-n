import java.util.Random;

public class Mapa {

	private static int dimension1 = 5;

	private static int dimension2 = 5;

	private Casilla [][] posiciones;

	public Mapa(){
		for (int i = 0; i < getDimension1(); i++)
			for (int j = 0; j < getDimension2(); j++) {
				int pobMax = getRandomNumberInts(500,5000);
				int comidaMax = getRandomNumberInts(pobMax,pobMax*3);
				int comida = getRandomNumberInts(0,comidaMax);
				int productividad = getRandomNumberInts(2,4);
				posiciones[i][j] = new Casilla(comida, comidaMax, productividad, null);
			}
	}

	public Casilla getMapaPosXY(int posicion1, int posicion2){
		Casilla valor=posiciones[posicion1][posicion2];
		return valor;
	}

	@Override
	public String  toString(){
		String imprimirLinea="";

		String[] imprimirContenido=new String[this.getDimension1()];

		for (int i = 0; i < imprimirContenido.length; i++) {
			imprimirContenido[i]="Row number= "+i +"---> ";
		}

		for (int i = 0; i < this.getDimension1(); i++) {
			for (int j = 0; j < this.getDimension2(); j++) {
				imprimirContenido[i]+="["+posiciones[i][j]+"]";
			}
			imprimirLinea+="[  "+ imprimirContenido[i] +" ] \n";
		}
		return imprimirLinea;
	}

	public int getDimension2() {
		// TODO Auto-generated method stub
		return this.dimension2;
	}

	public  int getDimension1() {
		return this.dimension1;
	}

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}


}
