package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Pais {

	// Nombre del país
	private String nombre;
	// id del país
	private int id;
	// Índices de las 3 estrategias que utiliza el país
	private int [] genoma;
	// Conjunto de casillas pertenecientes al país
	private ArrayList<Casilla> territorio;
	// Cantidad de comida que cuesta crear un civil o un militar
	private int precioCrearPoblacion = 1;
	// game.Mapa al que pertenece el país
	private Mapa mapa;
	// game.Casilla que ha conquistado en el turno actual, pendiente de asignársela al final del turno
	private ArrayList<Casilla> justConquistadas = new ArrayList<Casilla>();

	public Pais(String nombre, int id, Mapa mapa, int [] genoma){
		this.territorio = new ArrayList<Casilla>();
		this.id = id;
		this.mapa = mapa;
		this.nombre = nombre;
		this.genoma = genoma;
	}

	// Método para que cada casilla realice una acción.
	// Consiste en acciones sobre las que el país tiene control, la "elección" del jugador.
	// Ejemplos: Ataques, conquistas, mov. de comida, crear población, etc.
	public void realizarTurnoPais(){
		for (Casilla casilla : this.territorio)
			casilla.realizarTurno();
	}

	// Método para que el entorno (mapa) realice sus acciones.
	// Consiste en acciones de las que el país no tiene control, "naturales", las reglas del juego.
	// Se realizan después del turno del país. Ejemplos:
	// Muere pob. si falta comida, todos consumen comida, los civiles producen comida, etc.
	public void terminaTurno() {

		ArrayList<Casilla> casillasPerdidas = new ArrayList<Casilla>();
		for (Casilla justConquistada : this.getJustConquistadas())
			this.addTerritorio(justConquistada);
		this.clearJustConquistadas();
		for (Casilla casilla : this.territorio) {
			int comidaProducida = casilla.getPobCivil() * casilla.getProductividad();
			int pobAAbastecer = casilla.getPobTotal();
			// Se crea comida
			while (casilla.getComida() < casilla.getComidaMax() && 0 < comidaProducida) {
				casilla.addComida(1);
				comidaProducida--;
			}
			while (0 < this.getComida() && 0 < pobAAbastecer) {
				casilla.subComida(1);
				pobAAbastecer--;
			}
			// Muere gente
			while (casilla.getComida() < casilla.getPobTotal() && 0 < casilla.getPobCivil() && 0 < casilla.getPobMilitar()) {
				casilla.subPobMilitar(1);
				casilla.subPobCivil(1);
			}
			while (casilla.getComida() < casilla.getPobTotal() && 0 < casilla.getPobCivil()) {
				casilla.subPobCivil(1);
			}
			while (casilla.getComida() < casilla.getPobTotal() && 0 < casilla.getPobMilitar()) {
				casilla.subPobMilitar(1);
			}
			if ((casilla.getPobMilitar() <= 0 && casilla.getPobCivil() <= 0) || casilla.getPobTotal() <= 0) {
				casillasPerdidas.add(casilla);
			}
		}
		for (Casilla casilla : casillasPerdidas) {
			this.rmTerritorio(casilla);
			casilla.neutralizarCasilla();
		}
		this.printCasillas();
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
		if (nuevaCasilla.getPais() != null && nuevaCasilla.getPais() != this)
			nuevaCasilla.getPais().rmTerritorio(nuevaCasilla);
		nuevaCasilla.setPais(this);
	}
	public void rmTerritorio (Casilla casilla) {
		this.territorio.remove(casilla);
		casilla.setPais(null);
	}

	public int getComida() {
		int comida = 0;
		for (Casilla casilla : this.territorio)
			comida += casilla.getComida();
		return  comida;
	}
	public int getPobCivil() {
		int pob = 0;
		for (Casilla casilla : this.territorio)
			pob += casilla.getPobCivil();
		return  pob;
	}
	public int getPobMilitar() {
		int pob = 0;
		for (Casilla casilla : this.territorio)
			pob += casilla.getPobMilitar();
		return  pob;
	}

	public void printCasillas() {
		String imprimir;
		for (Casilla casilla : this.territorio)
			System.out.println(casilla);
		System.out.println();

	}

	@Override
	public String toString(){
		String imprimir="";
		imprimir="\nPAÍS: "+ this.getName() + ". Genoma: "+ Arrays.toString(this.genoma)+"\n";
		imprimir+="Población civil:"+this.getPobCivil()+ "; Población militar:"+this.getPobMilitar()+" ; Comida:"+this.getComida();
		return imprimir;
	}

}
