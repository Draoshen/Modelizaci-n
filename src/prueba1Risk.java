import java.util.Random;

public class prueba1Risk {
	
	public static int generateRandomInt(int upperRange){
	    Random random = new Random();
	    return random.nextInt(upperRange);
	}

public static void main(String args[]){
	
	Pais google=new Pais("Google",0,0,0);
	Mapa espania=new Mapa();
	System.out.println(google);
	google.Mapa(espania);
	google.getMapa();
	int numPaises=7;
	int maxRange=10000;
	
	Pais[] listaPaises=new Pais[numPaises];
	int[] random1=new int[listaPaises.length],random2=new int[listaPaises.length], random3=new int[listaPaises.length];
	for (int i = 0; i < listaPaises.length; i++) {
		random1[i]=generateRandomInt(maxRange);
		random2[i]=generateRandomInt(maxRange);
		random3[i]=generateRandomInt(maxRange);
		
	}
	for (int i = 0; i <listaPaises.length ; i++) {
		listaPaises[i]=new Pais("Pais "+i,random1[i],random2[i],random3[i]);
		System.out.println(listaPaises[i]+"\n");
	}
	
	}
}

class Pais{
	
	private int comida;
	private int poblacion;
	private int militares;
	static Mapa mapa;
	private String Nombre;
	private int identificador;
	
	public Pais(String nombre,int comida,int poblacion, int militares){
		this.comida=comida;
		this.poblacion=poblacion;
		this.militares=militares;
		this.Nombre=nombre;
	}
	
	public void Mapa(Mapa map){
		this.mapa=map;
	}
	
	public void getMapa(){
		System.out.println(this.mapa);
	}
	
	public String getName(){
		return this.Nombre;
	}
	
	public int getId(){
		return this.identificador;
	}
	
	@Override
	public String toString(){
		String imprimir="";
		
		imprimir="Pais: "+this.getName()+"\n";
		imprimir+="poblacion:"+this.poblacion+ "; militares:"+this.militares+" ;comida:"+this.comida;
		return imprimir;
	}
	
	
}

class Mapa{
	
	
	private static int dimension2=4;

	private static int dimension1=4;
	
	private int[][] posiciones=new int[dimension1][dimension2];
	
	public Mapa(){
	
		
		for (int i = 0; i <getDimension1(); i++){
			for (int j = 0; j < getDimension2(); j++) {
					posiciones[i][j]=0;
			}
			
		}
		
	}
	
	public int getMapaPosXY(int posicion1, int posicion2){
		int valor=posiciones[posicion1][posicion2];
		
		return valor;
		
	}
	
	public void setMapaPosXY(int posicion1,int posicion2, int idPais){
		
		 posiciones[posicion1][posicion2]=idPais;
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

	



}










