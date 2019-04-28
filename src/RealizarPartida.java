import java.util.Random;
public class RealizarPartida {

	public static void main(String args[]){

		int [] estragegiasGoogle = new int [] {1,1,1};
		Mapa Hispania = new Mapa();
		Pais google = new Pais("Google",Hispania,0,0,0,estragegiasGoogle);
		System.out.println(google);
		google.getTerritorio();
		int numPaises=2;
		int maxRange=5000;
		int numTurnos=1;


		Pais[] listaPaises=new Pais[numPaises];

		/*
		int[] random1 = new int[listaPaises.length],random2 = new int[listaPaises.length], random3 = new int[listaPaises.length];
		for (int i = 0; i < listaPaises.length; i++) {
			random1[i]=generateRandomInt(maxRange);
			random2[i]=generateRandomInt(maxRange);
			random3[i]=generateRandomInt(maxRange);
		} */

		for (int i = 0; i <listaPaises.length ; i++) {
			int [] estrategias = new int [] {i+1, i+1, i+1, 0 , 1 , 2};
			listaPaises[i]=new Pais("Pais "+ (i+1), Hispania,0,0,0, estrategias);
			Casilla casillaInicial = Hispania.getCasilla(getRandomNumberInts(0,Hispania.getDimensionX()-1),getRandomNumberInts(0,Hispania.getDimensionY()-1));
			while (casillaInicial.getPais()!= null) {
				casillaInicial = Hispania.getCasilla(getRandomNumberInts(0,Hispania.getDimensionX()-1),getRandomNumberInts(0,Hispania.getDimensionY()-1));
			}
			casillaInicial.addPobCivil(333);
			casillaInicial.addPobMilitar(167);
			casillaInicial.setPais(listaPaises[i]);
			listaPaises[i].addTerritorio(casillaInicial);
			System.out.println(listaPaises[i]+"\n");
			listaPaises[i].printCasillas();
		}

		//Aqui es donde se va realizando la partida
		int turno = 1;
		while(turno <= numTurnos){
			for (Pais pais: listaPaises) {
				pais.realizarTurnoPais();
			}
			turno++;
		}
	}

	public static int getRandomNumberInts(int min, int max){
		Random random = new Random();
		return random.ints(min,(max+1)).findFirst().getAsInt();
	}
	

}











