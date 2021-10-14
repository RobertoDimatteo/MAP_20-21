package mining;

import java.io.*;

// modella un intervallo reale [inf ,sup[
@SuppressWarnings("serial")
public class Interval implements Serializable {

	// ATTRIBUTI

	private float inf; // estremo inferiore
	private float sup; // estremo superiore

	// COSTRUTTORE

	// Avvalora i due attributi inf e sup con i parametri del
	// costruttore
	Interval(float inf, float sup) {
		this.inf = inf;
		this.sup = sup;
	}

	// METODI

	// avvalora inf con il parametro passato
	void setInf(float inf) {
		this.inf = inf;
	}

	// avvalora sup con il parametro passato
	void setSup(float sup) {
		this.sup = sup;
	}

	// restituisce inf
	float getInf() {
		return inf;
	}

	// restituisce sup
	float getSup() {
		return sup;
	}

	// restituisce vero se il parametro è maggiore uguale di inf e minore di sup,
	// false altrimenti
	boolean checkValueInclusion(float value) {
		return value >= inf && value < sup;
	}

	// rappresenta in una stringa gli estremi dell’intervallo e restituisce tale
	// stringa
	public String toString() {
		return "[" + inf + "," + sup + "[";
	}
}
