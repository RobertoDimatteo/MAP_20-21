package mining;

import data.Data;
import data.DiscreteAttribute;
import data.ContinuousAttribute;
import java.util.*;
import java.io.*;

/**
 * Classe che rappresenta un itemset (o pattern) frequente.
 */
class FrequentPattern implements Iterable<Item>, Comparable<FrequentPattern>, Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lista che contiene i riferimenti a oggetti istanza della classe &lt;Item&gt;
	 * che definiscono il frequent pattern.
	 * 
	 * @uml.property name = "fp"
	 * @uml.associationEnd multiplicity = "(0 -1)"
	 */
	private LinkedList<Item> fp;

	/**
	 * Valore di supporto calcolato per il pattern &lt;fp&gt;.
	 * 
	 * @uml.property name = "support"
	 */
	private float support;

	// COSTRUTTORI

	/**
	 * Costruttore che alloca &lt;fp&gt; come lista di item.
	 */
	FrequentPattern() {
		fp = new LinkedList<Item>();
	}

	/**
	 * Costruttore che alloca &lt;fp&gt; e &lt;support&gt; come copia del frequent
	 * pattern &lt;FP&gt; passato come parametro.
	 * 
	 * @param FP frequent pattern da copiare
	 */
	FrequentPattern(FrequentPattern FP) {
		fp = new LinkedList<Item>();
		for (Item item : FP)
			fp.add(item);

		support = FP.getSupport();

	}

	// METODI

	/**
	 * Inserisce nell'ultima posizione di &lt;fp&gt; l’argomento della procedura.
	 * 
	 * @param item generico item
	 */
	void addItem(Item item) {
		fp.addLast(item);
	}

	/**
	 * Restituisce l'item in posizione &lt;index&gt; di &lt;fp&gt;
	 * 
	 * @param index valore numerico che rappresenta un indice
	 * 
	 * @return item che si trova in posizione &lt;index&gt; di &lt;fp&gt;
	 */
	Item getItem(int index) {
		return fp.get(index);
	}

	/**
	 * Restituisce il membro &lt;support&gt;.
	 * 
	 * @return valore di supporto calcolato per il pattern
	 */
	float getSupport() {
		return support;
	}

	/**
	 * Restituisce la dimensione (lunghezza) di &lt;fp&gt;.
	 * 
	 * @return lunghezza del pattern
	 */
	int getPatternLength() {
		return fp.size();
	}

	/**
	 * Restituisce il membro &lt;fp&gt;.
	 * 
	 * @return lista di item
	 */
	LinkedList<Item> getFP() {
		return fp;
	}

	/**
	 * Calcola il supporto del pattern rappresentato dall'oggetto &lt;this&gt;
	 * rispetto al dataset &lt;data&gt; passato come argomento.
	 * 
	 * @param data insieme delle transazioni
	 * 
	 * @return valore di supporto del pattern nel dataset &lt;data&gt;
	 */
	float computeSupport(Data data) {
		int suppCount = 0;
		// indice esempio
		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			// indice item
			boolean isSupporting = true;
			for (Item it : this) {
				if (it instanceof DiscreteItem) {
					// DiscreteItem
					DiscreteItem item = (DiscreteItem) it;
					DiscreteAttribute attribute = (DiscreteAttribute) item.getAttribute();
					// Extract the example value
					Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
					if (!item.checkItemCondition(valueInExample)) {
						isSupporting = false;
						break; // the ith example does not satisfy fp
					}
				}
				if (it instanceof ContinuousItem) {
					// ContinuousItem
					ContinuousItem item = (ContinuousItem) it;
					ContinuousAttribute attribute = (ContinuousAttribute) item.getAttribute();
					// Extract the example value
					Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
					if (!item.checkItemCondition(valueInExample)) {
						isSupporting = false;
						break; // the ith example does not satisfy fp
					}
				}
			}
			if (isSupporting)
				suppCount++;
		}
		return ((float) suppCount) / (data.getNumberOfExamples());

	}

	/**
	 * Assegna al membro &lt;support&gt; il parametro della procedura.
	 * 
	 * @param support valore di supporto calcolato per il pattern
	 */
	void setSupport(float support) {
		this.support = support;
	}

	/**
	 * Scandisce &lt;fp&gt; per concatenare in una stringa la rappresentazione degli
	 * item e alla fine viene concatenato anche il supporto.
	 * 
	 * @return stringa concatenata
	 */
	public String toString() {
		String value = "";
		int fpCont = 1;
		for (Item it : fp) {
			if (fpCont < fp.size()) {
				value += it + " AND ";
			} else {
				value += it + "[" + support + "]";
			}
			fpCont++;
		}
		return value;
	}

	/**
	 * Restituisce un oggetto iteratore della classe &lt;Item&gt; usato per scandire
	 * l'oggetto fp.
	 * 
	 * @return un oggetto iteratore
	 */
	public Iterator<Item> iterator() {
		return fp.iterator();
	}

	/**
	 * Confronta i pattern rispetto al valore del loro supporto.
	 * 
	 * @param o un frequent pattern da confrontare
	 * 
	 * @return 0 se il supporto è uguale; 1 se il primo supporto è minore del
	 *         secondo; -1 altrimenti.
	 */
	public int compareTo(FrequentPattern o) {
		if (o.getSupport() == getSupport()) {
			return 0;
		} else if (o.getSupport() < getSupport()) {
			return 1;
		} else
			return -1;
	}

}
