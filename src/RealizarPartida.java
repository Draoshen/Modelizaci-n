import java.util.Random;
public class RealizarPartida {

	public static void main(String args[]){

		Pais google = new Pais("Google",0,0,0);
		Mapa Hispania = new Mapa();
		System.out.println(google);
		google.getTerritorio();
		int numPaises=2;
		int maxRange=5000;
		int numTurnos=10;


		Pais[] listaPaises=new Pais[numPaises];

		/*
		int[] random1 = new int[listaPaises.length],random2 = new int[listaPaises.length], random3 = new int[listaPaises.length];
		for (int i = 0; i < listaPaises.length; i++) {
			random1[i]=generateRandomInt(maxRange);
			random2[i]=generateRandomInt(maxRange);
			random3[i]=generateRandomInt(maxRange);
		} */

		for (int i = 0; i <listaPaises.length ; i++) {
			listaPaises[i]=new Pais("Pais "+i,0,0,0);
			Casilla casillaInicial = Hispania.getMapaPosXY(getRandomNumberInts(0,Hispania.getDimension1()-1),getRandomNumberInts(0,Hispania.getDimension2()-1));
			while (casillaInicial.getPais()!= null) {
				casillaInicial = Hispania.getMapaPosXY(getRandomNumberInts(0,Hispania.getDimension1()-1),getRandomNumberInts(0,Hispania.getDimension2()-1));
			}
			casillaInicial.addPobCivil(333);
			casillaInicial.addPobMilitar(167);
			casillaInicial.setPais(listaPaises[i]);
			listaPaises[i].addTerritorio(casillaInicial);
			System.out.println(listaPaises[i]+"\n");
			listaPaises[i].printCasillas();
		}

		//Aqui es donde se va realizando la partida
		while(numTurnos<10){

			for (Pais pais: listaPaises) {
				pais.realizarTurnoPais();
			}
			numTurnos++;
		}
	}

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}
	

}











