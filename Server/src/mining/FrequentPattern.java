package mining;

import data.Data;
import data.DiscreteAttribute;
import data.ContinuousAttribute;
import java.util.*;
import java.io.*;

// classe FrequentPattern che rappresenta un itemset (o pattern) frequente.
@SuppressWarnings("serial")
class FrequentPattern implements Iterable<Item>, Comparable<FrequentPattern>, Serializable {

	// ATTRIBUTI

	private LinkedList<Item> fp; // array che contiene riferimenti a oggetti istanza della classe Item che
	// definiscono il pattern.
	private float support; // valore di supporto calcolato per il pattern fp.

	// COSTRUTTORI

	// costruttore che alloca fp come array di dimensione 0.
	public FrequentPattern() {
		fp = new LinkedList<Item>();
	}

	// costruttore che alloca fp e support come copia del frequent pattern FP
	// passato.
	public FrequentPattern(FrequentPattern FP) {
		fp = new LinkedList<Item>();
		for (Item item : FP)
			fp.add(item);

		support = FP.getSupport();

	}

	// METODI

	// si inserisce in ultima posizione l’argomento della procedura.
	void addItem(Item item) {
		fp.addLast(item);
	}

	// restituisce l'item in posizione index di fp.
	public Item getItem(int index) {
		return fp.get(index);
	}

	// restituisce il membro support.
	public float getSupport() {
		return support;
	}

	// restituisce la dimensione (lunghezza) di fp
	public int getPatternLength() {
		return fp.size();
	}

	public LinkedList<Item> getFP() {
		return fp;
	}

	// calcola il supporto del pattern rappresentato dall'oggetto this rispetto al
	// dataset data passato come argomento.
	protected float computeSupport(Data data) {
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

	// assegna al membro support il parametro della procedura.
	void setSupport(float support) {
		this.support = support;
	}

	// si scandisce fp al fine di concatenare in una stringa la rappresentazione
	// degli item. Alla fine si concatena il supporto.
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

	@Override
	public Iterator<Item> iterator() {
		return fp.iterator();
	}

	@Override
	// confronto tra pattern rispetto al supporto.
	public int compareTo(FrequentPattern o) {
		if (o.getSupport() == getSupport()) {
			return 0;
		} else if (o.getSupport() < getSupport()) {
			return 1;
		} else
			return -1;
	}

}
