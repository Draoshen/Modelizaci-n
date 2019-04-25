import java.util.ArrayList;

public class Pais {

	private ArrayList<Casilla> territorio;
	private int comida;
	private int pobCivil;
	private int pobMilitar;
	static Mapa mapa;
	private String nombre;
	private int identificador;

	public Pais(String nombre,int comida,int pobCivil, int pobMilitar){
		this.territorio = new ArrayList<Casilla>();
		this.comida=comida;
		this.pobCivil=pobCivil;
		this.pobMilitar = pobMilitar;
		this.nombre=nombre;
	}

	public void Mapa(Mapa map){
		this.mapa=map;
	}

	public void getMapa(){
		System.out.println(this.mapa);
	}

	public String getName(){
		return this.nombre;
	}

	public int getId(){
		return this.identificador;
	}

	@Override
	public String toString(){
		String imprimir="";

		imprimir="Pais: "+this.getName()+"\n";
		imprimir+="poblacion:"+this.pobCivil+ "; militares:"+this.pobMilitar+" ;comida:"+this.comida;
		return imprimir;
	}

}
