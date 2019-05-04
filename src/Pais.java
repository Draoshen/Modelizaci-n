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

	public void realizarTurnoPais(){
		this.printCasillas();
		for (Casilla casilla : this.territorio) {
			casilla.realizarTurno();
		}
		for (Casilla justcon: this.justConquistadas){
			System.out.println("Conquistada "+ justcon.getCoordenadas());
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
