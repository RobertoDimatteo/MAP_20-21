package mining;

import java.util.Iterator;

//classe EmergingPattern che estende FrequentPattern e modella un pattern emergente.
@SuppressWarnings("serial")
class EmergingPattern extends FrequentPattern {

	// ATTRIBUTI

	private float growrate; // grow rate del pattern.

	// COSTRUTTORE

	// chiama il costruttore della superclasse passandogli fp e inizializza il
	// membro growrate con l’argomento del costruttore.
	public EmergingPattern(FrequentPattern fp, float growrate) {
		super(fp);
		this.growrate = growrate;
	}

	// METODI

	// restituisce il valore del membro growrate.
	public float getGrowRate() {
		return growrate;
	}

	// Si crea e restituisce la stringa che rappresenta il pattern,il suo
	// supporto e il suo growrate (fare uso del toString() ereditato da
	// FrequentPattern.
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
