package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Casilla {

	// TODO: ARREGLAR PROBLEMAS:
	// - Ataca siempre (con cualquier número de militares). Propuesta: ataca si tiene un porcentaje del total de población, si no crea pob.
	// - Conquista siempre (con cualquier número de población). Propuesta: conquista si tiene un porcentaje del total de población, si no crea pob.
	// - Crea pob siempre (con cualquier número de población). Propuesta: si ya tiene un porcentaje del total y enemigos cerca, ataca.

	// TODO: otro problema: en ocasiones las casillas no realizan ninguna acción, pero pone que se han conquistado algunas y al acabar el turno, que se han perdido//
	// si después de arreglarlo sigue igual, otro problema: o no cumple las condiciones de conquista, o muere al final del turno por algún motivo,
	// o descarta la opción de conquistar

	// TODO: añadir condiciones de conquista: la pob civil que se queda /se mata tras un ataque


	// ATRIBUTOS QUE TAMBIÉN TIENE EL PAÍS
	// Cantidad de comida en la casilla
	private int comida;
	// Número de civiles en la casilla
	private int pobCivil;
	// Número de militares en la casilla
	private int pobMilitar;
	// Población total de la casilla
	private int pobTotal;
	// Número de militares enemigos en la casilla
	private int militaresEnem;

	// ATRIBUTOS PROPIOS DE UNA CASILLA
	// Coordenadas que le corresponden en el mapa
	private int [] coordenadas;
	// Población máxima que puede haber en la casilla
	private int pobMax;
	// País al que pertenece la casilla. Si es neutral, vale null
	private Pais pais;
	// Comida máxima que puede almacenarse en la casilla
	private final int comidaMax;
	// TODO: el siguiente atributo quizás aumente demasiado la duración de la partida. Quizás borrarlo.
	// Número mínimo de población necesaria para conquistar la casilla
	private int minPobParaConquistar;
	// Productividad: Unidades de comida que produce cada civil en cada turno
	private final int productividad;
	// Lista con las casillas adyacentes a la casilla actual (pueden ser 2 (casilla esquina), 3 (casilla borde) o 4 (casilla interior))
	private ArrayList<Casilla> adyacentes;

	public Casilla (int comida, int comidaMax, int productividad, int pobMax, Pais pais, int [] coordenadas, ArrayList<Casilla> adyacentes) {
		this.comida = comida;
		this.comidaMax = comidaMax;
		this.productividad = productividad;
		this.pais = pais;
		this.pobMax = pobMax;
		this.pobCivil = 0;
		this.pobMilitar = 0;
		this.militaresEnem = 0;
		this.coordenadas = coordenadas;
		this.pobTotal = 0;
		this.adyacentes = adyacentes;
		this.minPobParaConquistar = this.pobMax / 20;
	}

	// Método para realizar una acción en el turno
	public void realizarTurno(){
		eligeAccion(this.pais.getGenoma());
	}

	// Método para elegir el movimiento a realizar
	private void eligeAccion (int [] genoma){
		// Acceder a genoma[3] (cuarto locus del genoma)
		// Dependiendo del número que sea, se realiza una acción u otra
		int result = 0;
		int genPrioridad;
		for (genPrioridad = 3; genPrioridad < 6 && result == 0; genPrioridad++)
			result = eligeGen(genoma, genPrioridad);
		genPrioridad -= 1;
		// Si result sigue siendo 0, no se ha realizado ninguna acción, la casilla no hace nada en este turno
	}

	private int eligeGen (int [] genoma, int genPrioridad) {
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
			default:
				return 0;
		}
	}

	// ACCIONES A REALIZAR SEGÚN LA POLÍTICA DEL PAÍS

	// Return: número de personas movidas
	private int politicaMoverPoblacion (int politica) {
		Casilla casillaDestino = null;
		switch (politica) {
			// Estrategia 1
			case 1:
				// Intenta ATACAR: solo mueve militares a casilla enemiga
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null && this.getPobMilitar() > 3) {
					return this.atacarCasilla(casillaDestino, this.getPobMilitar()*3/4);
				}
				casillaDestino = null;
				// Intenta CONQUISTAR: mueve civiles y militares a casilla neutral
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				// Si ya tenemos casilla que invadir, vamos a comprobar que cumplimos los requisitos para conquistar.
				// Al menos debemos tener un tercio de la población máxima de esa casilla, para evitar tener casillas con muy poca gente
				// TODO: la condición para poder conquistar es tener al menos xxx población
				if ((casillaDestino != null) && (casillaDestino.getMinPobParaConquistar()/2 < this.pobCivil/2)
						&& (casillaDestino.getMinPobParaConquistar()/2 < this.pobMilitar/2)) {
				//if (casillaDestino != null) {
					int pobCivilActual = this.pobCivil;
					int pobMilitarActual = this.pobMilitar;
					int civilesAMover= 0;
					int militaresAMover = 0;
					//Calcular cuántos mover
					// De momento va a mover la mitad de los que tiene en la casilla
					/*
					while (0 < pobCivilActual && 0 < pobMilitarActual
							//da problemas && civilesAMover < casillaDestino.getMinPobParaConquistar()/2
							&& civilesAMover < this.pobCivil/2
							//da problemas && militaresAMover < casillaDestino.getMinPobParaConquistar() /2
							&& militaresAMover < this.pobMilitar/2) {
						civilesAMover ++; militaresAMover ++;
						pobCivilActual --; pobMilitarActual--;
					} */
					civilesAMover = this.pobCivil/2;
					militaresAMover = this.pobMilitar/2;
					if (civilesAMover > 0 && militaresAMover > 0)
						return this.conquistarCasilla(casillaDestino, civilesAMover,militaresAMover);
				}
				return 0;
			// Estrategia 2
			case 2:
				// Intenta CONQUISTAR: mueve civiles y militares a casilla neutral
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				// Si ya tenemos casilla que invadir, vamos a comprobar que cumplimos los requisitos para conquistar.
				// Al menos debemos tener un tercio de la población máxima de esa casilla, para evitar tener casillas con muy poca gente
				// TODO: la condición para poder conquistar es tener al menos xxx población
				if ((casillaDestino != null) && (casillaDestino.getMinPobParaConquistar()/2 < this.pobCivil/2)
						&& (casillaDestino.getMinPobParaConquistar()/2 < this.pobMilitar/2)) {
				//if (casillaDestino != null) {
					int pobCivilActual = this.pobCivil;
					int pobMilitarActual = this.pobMilitar;
					int civilesAMover= 0;
					int militaresAMover = 0;
					//Calcular cuántos mover
					// De momento va a mover la mitad de los que tiene en la casilla
					/*
					while (0 < pobCivilActual && 0 < pobMilitarActual
							//da problemas && civilesAMover < casillaDestino.getMinPobParaConquistar()/2
							&& civilesAMover < this.pobCivil/2
							//da problemas && militaresAMover < casillaDestino.getMinPobParaConquistar() /2
							&& militaresAMover < this.pobMilitar/2) {
						civilesAMover ++; militaresAMover ++;
						pobCivilActual --; pobMilitarActual--;
					} */
					civilesAMover = this.pobCivil/2;
					militaresAMover = this.pobMilitar/2;
					if (civilesAMover > 0 && militaresAMover > 0)
						return this.conquistarCasilla(casillaDestino, civilesAMover,militaresAMover);
				}
				casillaDestino = null;
				// Intenta ATACAR: solo mueve militares a casilla enemiga
				for (Casilla adyacente : this.adyacentes)
					if (adyacente.getPais() != this.getPais() && adyacente.getPais() != null && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null)
					return this.atacarCasilla(casillaDestino, this.getPobMilitar()*3/4);
				return 0;
			default:
				return 0;
		}
	}
	// Return: comida movida
	public int politicasMoverComida (int politica) {
		Casilla casillaDestino = null;
		boolean hayAdyacenteEnemiga = false;
		boolean adyacentesSinEnemigos = true;
		switch (politica) {
			// Estrategia 1: si hay adyacentes enemigas, desplaza comida a una adyacente amiga sin enemigos adyacentes
			// Si no hay enemigas adyacentes pero hay amigas adyacentes con necesidad de comida, le manda comida
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
			// Estrategia 2: al revés que la estrategia 1
			case 2:
				for (Casilla adyacente : this.adyacentes)
					if (adyacente.getComida() < adyacente.getPobTotal())
						return this.moverComida((adyacente.pobTotal - adyacente.getComida()),adyacente);
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
			default:
		}
		return 0;
	}

	public int politicasCrearPoblacion(int politica) {
		Casilla casillaDestino = null;
		switch (politica) {
			// Estrategia 1: Defensiva. Si hay adyacente enemiga, crea el máximo viable de militares. Si no, pero hay neutral adyacente, crea igual civiles y militares
			case 1:
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null) {
					int militaresCreados = 0;
					while (this.pobTotal < Math.min(this.comida ,this.pobMax))
						militaresCreados += this.crearPoblacion(0,1);
					return militaresCreados;
				}
				casillaDestino = null;
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null && this.pobTotal < this.comida) {
					int pobCreada = 0;
					while (this.pobTotal < Math.min(this.comida-1, this.pobMax-1))
						pobCreada += this.crearPoblacion(1,1);
					return pobCreada;
				}
				return 0;
			// Estrategia 2: Expansiva. Si hay adyacente neutral, crea igual civiles y militares. Si no, pero hay adyacente enemiga, crea máximo viable de militares.
			case 2:
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() == null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null && this.pobTotal < this.comida) {
					int pobCreada = 0;
					while (this.pobTotal < Math.min(this.comida-1, this.pobMax-1))
						pobCreada += this.crearPoblacion(1,1);
					return pobCreada;
				}
				casillaDestino = null;
				for (Casilla adyacente : this.adyacentes)
					if ((adyacente.getPais() != this.getPais() && adyacente.getPais() != null) && (casillaDestino == null || casillaDestino.getProductividad() < adyacente.getProductividad()))
						casillaDestino = adyacente;
				if (casillaDestino != null) {
					int militaresCreados = 0;
					while (this.pobTotal < Math.min(this.comida ,this.pobMax))
						militaresCreados += this.crearPoblacion(0,1);
					return militaresCreados;
				}
				return 0;
			default:
				return 0;
		}
	}


	// POSIBLES MOVIMIENTOS EN UN TURNO

	public int atacarCasilla (Casilla casillaDestino, int militaresAtaque) {
		int militaresMovidos = militaresAtaque;
		casillaDestino.addMilitaresEnem(militaresAtaque);
		this.subPobMilitar(militaresAtaque);
		// Ataca la casilla destino con menos militares de los que hay allí -> Pierde.
		if (casillaDestino.getMilitaresEnem() <= casillaDestino.getPobMilitar()) {
			casillaDestino.subPobMilitar(militaresAtaque);
			casillaDestino.setMilitaresEnem(0);
			militaresMovidos = militaresAtaque;
		}
		// Ataca la casilla destino con más militares de los que hay allí -> Gana.
		else {
			int excesoMilitares;
			casillaDestino.setMilitaresEnem(militaresAtaque-casillaDestino.getPobMilitar());
			casillaDestino.setPobMilitar(0);
			excesoMilitares = casillaDestino.getMilitaresEnem() - (casillaDestino.pobMax - casillaDestino.getPobCivil());
			if (excesoMilitares > 0) {
				this.addPobMilitar(excesoMilitares);
				casillaDestino.setMilitaresEnem(casillaDestino.getMilitaresEnem()-excesoMilitares);
				militaresMovidos = militaresAtaque - excesoMilitares;
			}
			casillaDestino.setPobMilitar(casillaDestino.getMilitaresEnem());
			casillaDestino.setMilitaresEnem(0);
			casillaDestino.getPais().rmTerritorio(casillaDestino);
			casillaDestino.setPais(this.pais);
			this.pais.addJustConquistadas(casillaDestino);
		}
		return militaresMovidos;
	}

	public int conquistarCasilla (Casilla casillaDestino, int civilesAMover, int militaresAMover) {
		casillaDestino.setPais(this.pais);
		this.pais.addJustConquistadas(casillaDestino);
		// Se llevan provisiones por si la casilla estuviera vacía
		this.subComida(2*(civilesAMover+militaresAMover));
		casillaDestino.addComida(2*(civilesAMover+militaresAMover));
		return this.moverPoblacion(casillaDestino,civilesAMover,militaresAMover);
	}

	// Acción de mover X población a una casilla adyacente
	public int moverPoblacion (Casilla casillaDestino, int civiles, int militares) {
		int civilesMovidos = 0;
		int militaresMovidos = 0;
		// todo: utilizar en los 3 siguientes while cuando vaya bien el comprobarMover... while (this.comprobarMoverPoblacion(casillaDestino,2)
		while ( (civilesMovidos < civiles)
				&& (militaresMovidos < militares)) {
			this.subPobCivil(1);
			this.subPobMilitar(1);
			casillaDestino.addPobCivil(1);
			casillaDestino.addPobMilitar(1);
			civilesMovidos++; militaresMovidos++;
		}
		// ESTE BUCLE PROBABLEMENTE SOBRE
		/*
		while (civilesMovidos < civiles
				//&& this.comprobarMoverPoblacion(casillaDestino,1)
		) {
			this.subPobCivil(1);
			casillaDestino.addPobCivil(1);
			civilesMovidos ++;
		} */
		// ESTE BUCLE PROBABLEMENTE SOBRE
		/*
		while (militaresMovidos < militares

				//&& this.comprobarMoverPoblacion(casillaDestino,1)
		) {
			this.subPobMilitar(1);
			casillaDestino.addPobMilitar(1);
			militaresMovidos ++;
		} */
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
		while (this.comprobarCrearPoblacion(2)
				&& (civilesCreados < civiles)
				&& (militaresCreados < militares)) {
			this.addPobCivil(1);
			this.addPobMilitar(1);
			this.subComida(this.pais.getPrecioCrearPoblacion()*2);
			civilesCreados++; militaresCreados++;
		}
		while (this.comprobarCrearPoblacion(1) && (civilesCreados < civiles)) {
			this.addPobCivil(1);
			this.subComida(this.pais.getPrecioCrearPoblacion());
			civilesCreados++;
		}
		while (this.comprobarCrearPoblacion(1) && (militaresCreados < militares)) {
			this.addPobMilitar(1);
			this.subComida(this.pais.getPrecioCrearPoblacion());
			militaresCreados++;
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
	public int getMilitaresEnem () {
		return this.militaresEnem;
	}
	public int getProductividad () {
		return this.productividad;
	}
	public int getComidaMax () {
		return this.comidaMax;
	}
	public Pais getPais () {
		return this.pais;
	}
	public int getPobMax() {
		return this.pobMax;
	}
	public int getMinPobParaConquistar () {
		return this.minPobParaConquistar;
	}

	public int [] getCoordenadas () {
		return this.coordenadas;
	}
	public void setAdyacentes (ArrayList<Casilla> adyacentes) {
		this.adyacentes = adyacentes;
	}
	public void setPobMilitar (int cantidad) {
		if (0 <= cantidad && cantidad <= (this.pobMax-this.pobCivil)) {
			this.pobMilitar = cantidad;
			this.pobTotal = this.pobCivil + this.pobMilitar;
		}
	}
	public void setMilitaresEnem (int cantidad) {
		this.militaresEnem = cantidad;
	}
	public void setPais (Pais pais) {
		this.pais = pais;
	}
	public ArrayList<Casilla> getAdyacentes () {
		return this.adyacentes;
	}
	public int addComida (int cantidad) {
		int anyadida = 0;
		if (this.comidaMax <= (this.comida + cantidad)) {
			anyadida = this.comidaMax - this.comida;
			this.comida = this.comidaMax;
		}
		else {
			this.comida += cantidad;
			anyadida = cantidad;
		}
		return anyadida;
	}
	public int addPobCivil (int cantidad) {
		int anyadida = 0;
		if (this.pobMax < (this.pobTotal + cantidad)) {
			anyadida = this.pobMax - this.pobTotal;
			this.pobCivil = this.pobCivil + (this.pobMax - this.pobTotal);
			this.pobTotal = this.pobMax;
		}
		else {
			anyadida = cantidad;
			this.pobCivil += cantidad;
			this.pobTotal += cantidad;
		}
		return anyadida;
	}
	public int addPobMilitar (int cantidad) {
		int anyadida = 0;
		if (this.pobMax < (this.pobTotal + cantidad)) {
			anyadida = this.pobMax - this.pobTotal;
			this.pobMilitar = this.pobMilitar + (this.pobMax - this.pobTotal);
			this.pobTotal = this.pobMax;
		}
		else {
			anyadida = cantidad;
			this.pobMilitar += cantidad;
			this.pobTotal += cantidad;
		}
		return anyadida;
	}
	public void addMilitaresEnem (int cantidad) {
		this.militaresEnem = cantidad;
	}
	public int subComida (int cantidad) {
		int restada = 0;
		if (this.comida < cantidad) {
			restada = cantidad - this.comida;
			this.comida = 0;
		}
		else {
			restada = cantidad;
			this.comida -= cantidad;
		}
		return restada;
	}
	public int subPobCivil (int cantidad) {
		int restada = 0;
		if (this.pobCivil < cantidad) {
			restada -= this.pobCivil;
			this.pobTotal -= this.pobCivil;
			this.pobCivil = 0;
		}
		else {
			restada = cantidad;
			this.pobCivil -= cantidad;
			this.pobTotal -= cantidad;
		}
		return restada;
	}
	public int subPobMilitar (int cantidad) {
		int restada = 0;
		if (this.pobMilitar < cantidad) {
			restada = this.pobMilitar;
			this.pobTotal = 0;
			this.pobMilitar = 0;
		}
		else {
			restada = cantidad;
			this.pobMilitar -= cantidad;
			this.pobTotal -= cantidad;
		}
		return restada;
	}
	public void neutralizarCasilla () {
		this.pais = null;
		this.pobTotal = 0;
		this.pobMilitar = 0;
		this.pobCivil = 0;
		this.militaresEnem = 0;
	}

	// Metodos auxiliares

	// TODO: ARREGLAR, NO FUNCIONA BIEN, DEVUELVE FALSE CUANDO NO DEBERÍA
	// Método auxiliar para comprobar si es viable mover población en cada momento
	private boolean comprobarMoverPoblacion (Casilla casillaDestino, int cantidad) {
		return ((casillaDestino.getPobTotal() < (casillaDestino.getPobMax()-(cantidad-1)))
				&& (casillaDestino.getPobTotal() < (casillaDestino.getComida()-(cantidad-1)))
				&& (this.pobTotal < (this.comida-1))
				&& (0 < (this.pobTotal-1))
		);
	}

	// Método auxiliar para comprobar si es viable mover comida en cada momento
	private boolean comprobarMoverComida (Casilla casillaDestino) {
		return ((casillaDestino.getComida() < casillaDestino.getComidaMax())
				&& (this.pobTotal < this.comida)
				&& (0 < this.comida));
	}

	// Método auxiliar para comprobar si es viable crear población en cada momento
	private boolean comprobarCrearPoblacion (int cantidad) {
		return (this.pobTotal < (this.pobMax-(cantidad-1))
				&& (this.pobTotal < (this.comida-(cantidad-1)))
				&& cantidad > 0);
	}

	@Override
	public String toString() {
		String imprimir = "";

		imprimir="Casilla: "+Arrays.toString(this.coordenadas)+"\n";
		imprimir+="Población civil:"+this.pobCivil+ "; Población militar:"+this.pobMilitar+"; Población total: "+this.pobTotal+"; Población máxima: "
				+ this.pobMax+" ; Comida: "+this.comida+ "; Comida máxima: "+ this.comidaMax+"; País: "+ this.pais.getName()
				+ "; Productividad: " + this.productividad;
		return imprimir;
	}
}
