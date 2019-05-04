import java.util.ArrayList;
import java.util.Arrays;

public class Casilla {

	// ATRIBUTOS QUE TAMBIÉN TIENE EL PAÍS
	// Cantidad de comida en la casilla
	private int comida;
	// Número de civiles en la casilla
	private int pobCivil;
	// Número de militares en la casilla
	private int pobMilitar;
	// Población total de la casilla
	private int pobTotal;

	// ATRIBUTOS PROPIOS DE UNA CASILLA
	// Coordenadas que le corresponden en el mapa
	private int [] coordenadas;
	// Población máxima que puede haber en la casilla
	private int pobMax;
	// País al que pertenece la casilla. Si es neutral, vale null
	private Pais pais;
	// Comida máxima que puede almacenarse en la casilla
	private final int comidaMax;
	// Productividad: Unidades de comida que produce cada civil en cada turno
	private final int productividad;
	// Lista con las casillas adyacentes a la casilla actual (pueden ser 2 (casilla esquina), 3 (casilla borde) o 4 (casilla interior))
	private ArrayList<Casilla> adyacentes;


	// TODO: limitar getters de la casilla para que solo la puedan usar casillas del mismo pais
	public Casilla (int comida, int comidaMax, int productividad, int pobMax, Pais pais, int [] coordenadas, ArrayList<Casilla> adyacentes) {
		this.comida = comida;
		this.comidaMax = comidaMax;
		this.productividad = productividad;
		this.pais = pais;
		this.pobMax = pobMax;
		this.pobCivil = 0;
		this.pobMilitar = 0;
		this.coordenadas = coordenadas;
		this.pobTotal = 0;
		this.adyacentes = adyacentes;
	}

	// Método para realizar una acción en el turno
	public void realizarTurno(){
		eligeAccion(this.pais.getGenoma());
	}

	// Método para elegir el movimiento a realizar
	private void eligeAccion (int [] genoma){
		// TODO: if (genoma.length != 6) return error;
		// TODO: crear clase genoma ?
		// Acceder a genoma[3] (cuarto espacio del genoma)
		// Dependiendo del número que sea, se realiza una acción u otra
		int result = 0;
		int genPrioridad;
		for (genPrioridad = 3; genPrioridad < 6 && result == 0; genPrioridad++)
			result = eligeGen(genoma, genPrioridad);
		genPrioridad -= 1;
		// Si result sigue siendo 0, no se ha realizado ninguna acción, la casilla no hace nada en este turno
		//System.out.println("GenPrioridad: " +(genPrioridad));
		// Imprimir info de la acción realizada:
		String accionRealizada;
		switch (genPrioridad) {
			case 3:
				accionRealizada = "Mover Población. Población movida: ";
				break;
			case 4:
				accionRealizada = "Mover Comida. Comida movida: ";
				break;
			case 5:
				if (result != 0) {
					accionRealizada = "Crear Población. Población creada: ";
					break;
				}
			default:
				accionRealizada = "Ninguna: ";
		}
		System.out.println("\nCasilla "+ Arrays.toString(this.coordenadas) + "Acción realizada: " + accionRealizada + result);
	}

	private int eligeGen (int [] genoma, int genPrioridad) {
		//System.out.println("Genoma y genPrioridad: " + Arrays.toString(genoma) + genPrioridad);
		switch (genoma[genPrioridad]) {
			case 0:
				// Se trata del primer gen del genoma (genoma[0]) que corresponde al movimiento de Mover Población
				return politicaMoverPoblacion(genoma[0]);
			case 1:
				// Se trata del segundo gen del genoma (genoma[1]) que corresponde al movimiento de Mover Comida
				return politicasMoverComida(genoma[1]);
			case 2:
				// Se trata del tercer gen del genoma (genoma[2]) que corresponde al movimiento de Crear Población
				return politicasCrearPoblacion(genoma[2]);
			default: return 0;
		}
	}

	// ACCIONES A REALIZAR SEGÚN LA POLÍTICA DEL PAÍS

	// TODO: establecer cuándo se ocupa una casilla
	// TODO: establecer cuándo se conquista una casilla
	// TODO: añadir condición para conquistar casilla (mover poblacion civil): mitad de la ocup maxima o tener pobl maxima en casilla actual
	// TODO: añadir condiciones de conquista: la pob civil que se queda /se mata
	// TODO: establecer que si se llega al máximo de comida, el sobrante se transforma en poblacion civil
	// TODO: establecer que se gaste comida en cada turno / muera gente por falta de comida
	// De momento, dos distinas políticas a la hora de mover población
	// Return: número de personas movidas
	private int politicaMoverPoblacion (int politica) {
		Casilla casillaDestino = null;
		switch (politica) {
			// Estrategia 1
			case 1:
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null) {
					int militaresAMover = this.getPobMilitar()*3/4;
					return this.moverPoblacion(0, militaresAMover, casillaDestino);
				}
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;

				if (casillaDestino != null) {
					int civilesAMover = this.getPobCivil() /2; // TODO: cambiar a máximo viable
					int militaresAMover = this.getPobMilitar() /2;
					casillaDestino.setPais(this.pais);
					this.pais.addJustConquistadas(casillaDestino);
					return this.moverPoblacion(civilesAMover,militaresAMover,casillaDestino);
				}
				return 0;
			// TODO: Estrategia 2
			case 2:
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null) {
					int civilesAMover = this.getPobCivil() /2; // TODO: cambiar a máximo viable
					int militaresAMover = this.getPobMilitar() /2;
					casillaDestino.setPais(this.pais);
					this.pais.addJustConquistadas(casillaDestino);
					return this.moverPoblacion(civilesAMover,militaresAMover,casillaDestino);
				}
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null)
					&& (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null) {
					int militaresAMover = this.getPobMilitar()*3/4;
					return this.moverPoblacion(0, militaresAMover, casillaDestino);
				}
				return 0;
			default:
				return 0; // TODO: mejor debería devolver algún error?

		}
	}

	//TODO
	public int politicasMoverComida (int politica) {
		Casilla casillaDestino = null;
		boolean hayAdyacenteEnemiga = false;
		boolean adyacentesSinEnemigos = true;
		switch (politica) {
			case 1:
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null))
						hayAdyacenteEnemiga = true;
				if (hayAdyacenteEnemiga)
					for (Casilla adyacente : this.adyacentes)
						if (adyacente.getPais() == this.getPais()) {
							for (Casilla adyacenteR : adyacente.adyacentes)
								if (adyacenteR.getPais() != this.getPais() && adyacente.getPais() != null)
									adyacentesSinEnemigos = false;
							if (adyacentesSinEnemigos) casillaDestino = adyacente;
						}
					if (casillaDestino != null)
						return this.moverComida((this.comida - this.pobTotal), casillaDestino);
				for (Casilla adyacente : this.adyacentes)
					if (adyacente.getComida() < adyacente.getPobTotal())
						return this.moverComida((adyacente.pobTotal - adyacente.getComida()),adyacente);
		}
		return 0;
	}
	//TODO
	public int politicasCrearPoblacion(int politica) {
		return 0;
	}


	// POSIBLES MOVIMIENTOS EN UN TURNO


	// Acción de mover X población a una casilla adyacente
	// Distinguir casos: casilla conquistada, casilla neutral, casilla enemiga (solo militares)
	public int moverPoblacion (int civiles, int militares, Casilla casillaDestino) {
		int civilesMovidos = 0;
		int militaresMovidos = 0;
		while ( this.comprobarMoverPoblacion(casillaDestino)
				&& (civilesMovidos < civiles)
				&& (militaresMovidos < militares)) {
			this.subPobCivil(1);
			this.subPobMilitar(1);
			casillaDestino.addPobCivil(1);
			casillaDestino.addPobMilitar(1);
			civilesMovidos++; militaresMovidos++;
		}
		while (civilesMovidos < civiles && this.comprobarMoverPoblacion(casillaDestino)) {
			this.subPobCivil(1);
			casillaDestino.addPobCivil(1);
			civilesMovidos ++;
		}
		while (militaresMovidos < militares && this.comprobarMoverPoblacion(casillaDestino)) {
			this.subPobMilitar(1);
			casillaDestino.addPobMilitar(1);
			militaresMovidos ++;
		}
		return (civilesMovidos + militaresMovidos);
	}

	// Acción de mover X comida a una casilla adyacente
	public int moverComida (int cantidad, Casilla casillaDestino) {
		int comidaMovida = 0;
		while (this.comprobarMoverComida(casillaDestino)
				&& comidaMovida < cantidad) {
			this.subComida(1);
			casillaDestino.addComida(1);
			comidaMovida ++;
		}
		return comidaMovida;
	}

	// Acción de crear población a cambio de unas ciertas unidades de comida
	public int crearPoblacion (int civiles, int militares) {
		int civilesCreados = 0;
		int militaresCreados = 0;
		while (this.comprobarCrearPoblacion()
				&& (civilesCreados < civiles)
				&& (militaresCreados < militares)) {
			this.addPobCivil(1);
			this.addPobMilitar(1);
			this.subComida(this.pais.getPrecioCrearPoblacion());
			civilesCreados++; militaresCreados++;
		}
		return (civilesCreados + militaresCreados);
	}

	// GETTERS, SETTERS Y MODIFICADORES DE LOS ATRIBUTOS

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
	public ArrayList<Casilla> getAdyacentes () {
		return this.adyacentes;
	}
	public void addComida (int cantidad) {
		this.comida += cantidad;
		// TODO: exception limite comida
	}
	public void subComida (int cantidad) {
		this.comida -= cantidad;
		// TODO: exception comida insuficiente -> consume todo y se queda a 0
	}
	public void addPobCivil (int cantidad) {
		this.pobCivil += cantidad;
		this.pobTotal += cantidad;
		// TODO: exception limite poblacion
	}
	public void subPobCivil (int cantidad) {
		this.pobCivil -= cantidad;
		this.pobTotal -= cantidad;
		// TODO: exception pobCivil insuficiente -> mata todos y se queda a 0
	}
	public void addPobMilitar (int cantidad) {
		this.pobMilitar += cantidad;
		this.pobTotal += cantidad;
		// TODO: exception limite poblacion
	}
	public void subPobMilitar (int cantidad) {
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
	public void setAdyacentes (ArrayList<Casilla> adyacentes) {
		this.adyacentes = adyacentes;
	}

	// Metodos auxiliares
	// Método auxiliar para comprobar si es viable mover población en cada momento
	private boolean comprobarMoverPoblacion (Casilla casillaDestino) {
		return ((casillaDestino.getPobTotal() < (casillaDestino.getPobMax()-1))
				&& (casillaDestino.getPobTotal() < (casillaDestino.getComida()-1))
				&& (this.pobTotal < (this.comida-1))
				&& (0 < (this.pobTotal-1)));
	}
	// Método auxiliar para comprobar si es viable mover comida en cada momento
	private boolean comprobarMoverComida (Casilla casillaDestino) {
		return ((casillaDestino.getComida() < casillaDestino.getComidaMax())
				&& (this.pobTotal < this.comida)
				&& (0 < this.comida));
	}
	// Método auxiliar para comprobar si es viable crear población en cada momento
	private boolean comprobarCrearPoblacion () {
		return ((this.pobTotal < (this.pobMax-1))
				&& (this.pobTotal < (this.comida-1)));
	}

	@Override
	public String toString() {
		String imprimir = "";

		imprimir="\nCasilla: ("+this.coordenadas[0]+","+this.coordenadas[1]+")\n";
		imprimir+="Población civil:"+this.pobCivil+ "; Población militar:"+this.pobMilitar+"; Población máxima: "
				+ this.pobMax+" ; Comida: "+this.comida+ "; Comida máxima: "+ this.comidaMax+"; País: "+this.pais.getName()
				+ "; Productividad: " + this.productividad;
		return imprimir;
	}
}
