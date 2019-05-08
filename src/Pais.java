import java.util.ArrayList;
import java.util.Arrays;

public class Pais {

	// Nombre del país
	private String nombre;
	// id del país
	private int id;
	// Índices de las 3 estrategias que utiliza el país
	private int [] genoma = new int [6];
	// Conjunto de casillas pertenecientes al país
	private ArrayList<Casilla> territorio;
	// Cantidad de comida que cuesta crear un civil o un militar
	private int precioCrearPoblacion = 3;
	// Mapa al que pertenece el país
	private Mapa mapa;

	// Cantidad de comida actual en país
	private int comida;
	// Número de civiles en el país
	private int pobCivil;
	// Número de militares en el país
	private int pobMilitar;
	// Casilla que ha conquistado en el turno actual, pendiente de asignársela al final del turno
	private ArrayList<Casilla> justConquistadas = new ArrayList<Casilla>();


	public Pais(String nombre, int id, Mapa mapa, int comida,int pobCivil, int pobMilitar, int [] genoma){
		this.territorio = new ArrayList<Casilla>();
		this.id = id;
		this.mapa = mapa;
		this.comida = comida;
		this.pobCivil = pobCivil;
		this.pobMilitar = pobMilitar;
		this.nombre = nombre;
		this.genoma = genoma;
	}

	// Método para que cada casilla realice una acción.
	// Consiste en acciones sobre las que el país tiene control, la "elección" del jugador.
	// Ejemplos: Ataques, conquistas, mov. de comida, crear población, etc.
	public void realizarTurnoPais(){
		this.printCasillas();
		for (Casilla casilla : this.territorio) {
			casilla.realizarTurno();
		}
		for (Casilla justCon: this.justConquistadas){
			System.out.println("Conquistada "+ Arrays.toString(justCon.getCoordenadas()));
		}
	}

	// Método para que el entorno (mapa) realice sus acciones.
	// Consiste en acciones de las que el país no tiene control, "naturales", las reglas del juego.
	// Se realizan después del turno del país. Ejemplos:
	// Muere pob. si falta comida, todos consumen comida, los civiles producen comida, etc.
	// TODO: realiza acciones de fin de turno:
	// - consumir comida (pobTotal)
	// - producir comida (pobCivil, productividad)
	// - añadir territorios conquistados al país
	public void terminaTurno() {

		for (Casilla justConquistada : this.getJustConquistadas())
			this.addTerritorio(justConquistada);
		this.clearJustConquistadas();
		for (Casilla casilla : this.territorio) {
			casilla.addComida(casilla.getPobCivil() * casilla.getProductividad());
			casilla.subComida(casilla.getPobTotal());
			// TODO: si hacemos que estos métodos lancen excepciones, utilizar aquí un try catch
			// si no, con ifs comprobando que se cumplan las condiciones
			// if (falta comida) then mata gente
			// if (alcanzado maximo comida) then crea gente
			// if (poblacion esta a cero) then pais pierde casilla
		}
	}

	public int [] getGenoma () {
		return this.genoma;
	}

	public String getName(){
		return this.nombre;
	}

	public Mapa getMapa() { return  this.mapa; }

	public int getPrecioCrearPoblacion () { return this.precioCrearPoblacion; }

	public ArrayList<Casilla> getJustConquistadas () { return this.justConquistadas; }

	public void clearJustConquistadas () { this.justConquistadas = new ArrayList<Casilla>();}

	public void addJustConquistadas (Casilla justConquistada) { this.justConquistadas.add(justConquistada); }

	public ArrayList<Casilla> getTerritorio() {
		return this.territorio;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void addTerritorio(Casilla nuevaCasilla) {
		this.territorio.add(nuevaCasilla);
		this.pobCivil += nuevaCasilla.getPobCivil();
		this.pobMilitar += nuevaCasilla.getPobMilitar();
		this.comida += nuevaCasilla.getComida();
	}

	public void printCasillas() {
		String imprimir;
		for (Casilla casilla : this.territorio)
			System.out.print(casilla);
		System.out.println();

	}

	@Override
	public String toString(){
		String imprimir="";
		imprimir="\n\nPais: "+ this.getName() + ". Genoma: "+ Arrays.toString(this.genoma)+"\n";
		imprimir+="Población civil:"+this.pobCivil+ "; Población militar:"+this.pobMilitar+" ; Comida:"+this.comida;
		return imprimir;
	}

}
