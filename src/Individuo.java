import javax.naming.InitialContext;
import java.util.Arrays;
import java.util.Comparator;

public class Individuo {

	private int id;
	private int puntuacion;
	private int[] genoma;
	private boolean paraCruzar;
	private boolean paraEliminar;

	public Individuo(int[] genoma, int id) {
		this.genoma = genoma;
		this.id = id;
		this.puntuacion = 0;
		this.paraCruzar = false;
		this.paraEliminar = false;
	}
	public Individuo(int id) {
		this.id = id;
		this.genoma = new int [6];
		this.puntuacion = 0;
		this.paraCruzar = false;
		this.paraEliminar = false;
	}

	public Individuo() {
		this.genoma = new int[6];
	}

	public Individuo reproducirse(Individuo otroProgenitor) {
		return null;
	}

	public boolean isParaCruzar () {
		return this.paraCruzar;
	}

	public void setParaCruzar (boolean value) {
		this.paraCruzar = value;
	}

	public boolean isParaEliminar () {
		return this.paraEliminar;
	}

	public void setParaEliminar (boolean value) {
		this.paraEliminar = value;
	}

	public int getId () {
		return this.id;
	}

	public int[] getGenoma() {
		return this.genoma;
	}

	public void modGen(int pos, int value) {
		this.genoma[pos] = value;
	}

	public int getGen(int pos) {
		return this.genoma[pos];
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public void setPuntuacion(int nuevaPunt) {
		this.puntuacion = nuevaPunt;
	}

	@Override
	public String toString () {
		return "Individuo "+this.id+" con genoma "+ Arrays.toString(this.genoma) + " y puntuación: "+ this.puntuacion;
	}
}
