package paisesMOD;

public class Casilla {

	private int comida;
	private int [] coordenadas;
	private int pobCivil;
	private int pobMilitar;
	private int pobTotal;
	private int pobMax;
	private Pais pais; // TODO: Este atributo deberia ser un objeto Pais o solo su nombre?
	private final int comidaMax;
	private final int productividad;

	public Casilla (int comida, int comidaMax, int productividad, int pobMax, Pais pais, int [] coordenadas) {
		this.comida = comida;
		this.comidaMax = comidaMax;
		this.productividad = productividad;
		this.pais = pais;
		this.pobMax = pobMax;
		this.pobCivil = 0;
		this.pobMilitar = 0;
		this.coordenadas = coordenadas;
		this.pobTotal = 0;
	}

	public int getComida () {
		return this.comida;
	}
	public int getPobCivil () {
		return  this.pobCivil;
	}
	public int getPobMilitar () {
		return this.pobMilitar;
	}
	public int getPobTotal () {
		return this.pobTotal;
	}
	public int getProductividad () {
		return this.productividad;
	}
	public int getComidaMax () {
		return this.comidaMax;
	}
	public void addComida (int cantidad) {
		this.comida += cantidad;
		// TODO: exception limite comida
	}
	public void eatComida (int cantidad) {
		this.comida -= cantidad;
		// TODO: exception comida insuficiente -> consume todo y se queda a 0
	}
	public void addPobCivil (int cantidad) {
		this.pobCivil += cantidad;
		this.pobTotal += cantidad;
		// TODO: exception limite poblacion
	}
	public void killPobCivil (int cantidad) {
		this.pobCivil -= cantidad;
		this.pobTotal -= cantidad;
		// TODO: exception pobCivil insuficiente -> mata todos y se queda a 0
	}
	public void addPobMilitar (int cantidad) {
		this.pobMilitar += cantidad;
		this.pobTotal += cantidad;
		// TODO: exception limite poblacion
	}
	public void killPobMilitar (int cantidad) {
		this.pobMilitar -= cantidad;
		this.pobTotal -= cantidad;
		// TODO: exception pobMilitar insuficiente -> mata todos y se queda a 0
	}
	public Pais getPais () {
		return this.pais;
	}
	public void setPais (Pais pais) {
		this.pais = pais;
	}
	public int getPobMax() {
		return this.pobMax;
	}
	public int [] getCoordenadas () {
		return this.coordenadas;
	}

	@Override
	public String toString() {
		String imprimir = "";

		imprimir="Casilla: ("+this.coordenadas[0]+","+this.coordenadas[1]+")\n";
		imprimir+="Poblacion civil:"+this.pobCivil+ "; Poblacion militar:"+this.pobMilitar+"; Poblacion maxima: "
				+ this.pobMax+" ; Comida: "+this.comida+ "; Comida maxima: "+ this.comidaMax+"; Pais: "+this.pais.getName()
				+ "; Productividad: " + this.productividad;
		return imprimir;
	}
}