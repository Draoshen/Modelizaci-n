import java.util.ArrayList;

public class Pais {

	private ArrayList<Casilla> territorio;
	private int comida;
	private int pobCivil;
	private int pobMilitar;
	private String nombre;

	public Pais(String nombre,int comida,int pobCivil, int pobMilitar){
		this.territorio = new ArrayList<Casilla>();
		this.comida=comida;
		this.pobCivil=pobCivil;
		this.pobMilitar = pobMilitar;
		this.nombre=nombre;
	}


	public String getName(){
		return this.nombre;
	}

	public ArrayList<Casilla> getTerritorio() {
		return this.territorio;
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
	}

	@Override
	public String toString(){
		String imprimir="";

		imprimir="\n\nPais: "+this.getName()+"\n";
		imprimir+="Población civil:"+this.pobCivil+ "; Población militar:"+this.pobMilitar+" ; Comida:"+this.comida;
		return imprimir;
	}

}
