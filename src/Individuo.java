import javax.naming.InitialContext;
import java.util.Comparator;

public class Individuo {

	private int puntuacion;
	private int[] genoma;
	private boolean paraCruzar;
	private boolean paraEliminar;

	public Individuo(int[] genoma) {
		this.genoma = genoma;
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

	public void setParaCruzar () {
		this.paraCruzar = true;
	}

	public boolean isParaEliminar () {
		return this.paraEliminar;
	}

	public void setParaEliminar () {
		this.paraEliminar = true;
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
}
