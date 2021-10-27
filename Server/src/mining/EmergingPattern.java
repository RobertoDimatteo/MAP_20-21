package mining;

import java.util.Iterator;

/**
 * Classe che estende &lt;FrequentPattern&gt; e modella un pattern emergente.
 */
class EmergingPattern extends FrequentPattern {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Grow rate del pattern.
	 */
	private float growrate;

	// COSTRUTTORE

	/**
	 * Costruttore che invoca il costruttore della superclasse
	 * &lt;FrequentPattern&gt; passandogli &lt;fp&gt; e inizializza il membro
	 * &lt;growrate&gt; con il valore passato come parametro.
	 * 
	 * @param fp       un pattern
	 * @param growrate grow rate del pattern
	 */
	public EmergingPattern(FrequentPattern fp, float growrate) {
		super(fp);
		this.growrate = growrate;
	}

	// METODI

	/**
	 * Restituisce il valore del membro &lt;growrate&gt;.
	 * 
	 * @return grow rate del pattern
	 */
	public float getGrowRate() {
		return growrate;
	}

	/**
	 * Crea la stringa che rappresenta il pattern, il suo supporto e il suo grow
	 * rate.
	 * 
	 * @return stringa concatenata
	 */
	public String toString() {
		String value = "";
		int epCont = 1;
		Iterator<Item> e = iterator();
		while (e.hasNext()) {
			if (epCont < getPatternLength()) {
				value += e.next() + " AND ";
			} else {
				value += e.next() + "[" + getSupport() + "]";
				value += "[" + growrate + "]";
			}
			epCont++;
		}
		return value;
	}

}
