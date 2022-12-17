package mining;

import java.io.*;

/**
 * Classe che modella un intervallo reale del tipo [inf ,sup[.
 */
class Interval implements Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Estremo inferiore.
	 */
	private float inf;

	/**
	 * Estremo superiore.
	 */
	private float sup;

	// COSTRUTTORE

	/**
	 * Costruttore che avvalora i membri &lt;inf&gt; e &lt;sup&gt; con i valori
	 * passati come parametri.
	 * 
	 * @param inf estremo inferiore
	 * @param sup estremo superiore
	 */
	Interval(float inf, float sup) {
		this.inf = inf;
		this.sup = sup;
	}

	// METODI

	/**
	 * Avvalora il membro &lt;inf&gt; con il parametro passato.
	 * 
	 * @param inf estremo inferiore
	 */
	void setInf(float inf) {
		this.inf = inf;
	}

	/**
	 * Avvalora il membro &lt;sup&gt; con il parametro passato.
	 * 
	 * @param sup estremo superiore
	 */
	void setSup(float sup) {
		this.sup = sup;
	}

	/**
	 * Restituisce il membro &lt;inf&gt;.
	 * 
	 * @return estremo inferiore
	 */
	float getInf() {
		return inf;
	}

	/**
	 * Restituisce il membro &lt;sup&gt;.
	 * 
	 * @return estremo superiore
	 */
	float getSup() {
		return sup;
	}

	/**
	 * Esegue un controllo sul parametro &lt;value&gt; per verificare se sia
	 * maggiore uguale di &lt;inf&gt; e minore di &lt;sup&gt;.
	 * 
	 * @param value valore assunto da un attributo continuo
	 * 
	 * @return vero se value è maggiore uguale di inf e minore di sup; falso
	 *         altrimenti.
	 */
	boolean checkValueInclusion(float value) {
		return value >= inf && value < sup;
	}

	/**
	 * Rappresenta in una stringa gli estremi dell’intervallo.
	 * 
	 * @return stringa rappresentante gli estremi dell'intervallo
	 */
	public String toString() {
		return "[" + inf + "," + sup + "[";
	}
	
}
