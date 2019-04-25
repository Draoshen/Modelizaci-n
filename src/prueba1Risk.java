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
	int numPaises=10;
	int maxRange=5000;

	Pais[] listaPaises=new Pais[numPaises];
	int[] random1 = new int[listaPaises.length],random2 = new int[listaPaises.length], random3 = new int[listaPaises.length];
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











