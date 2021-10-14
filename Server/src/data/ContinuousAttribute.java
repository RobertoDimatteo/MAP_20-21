package data;

import java.util.Iterator;

//classe ContinuousAttribute che estende la classe Attribute e
//modella un attributo continuo (numerico) rappresentandone il dominio [min,max]
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute implements Iterable<Float>{

	// ATTRIBUTI

	private float min; // rappresentano gli estremi di un intervallo
	private float max; // rappresentano gli estremi di un intervallo

	// COSTRUTTORE

	// Invoca il costruttore della classe madre e avvalora i membri
	public ContinuousAttribute(String name, int index, float min, float max) {
		super(name, index);

		this.min = min;
		this.max = max;
	}

	// METODI

	// Restituisce il valore del membro min
	public float getMin() {
		return min;
	}

	// Restituisce il valore del membro max
	public float getMax() {
		return max;
	}

	@Override
	// istanzia e restituisce un riferimento ad oggetto di classe
	// ContinuousAttributeIterator con numero di intervalli di discretizzazione pari
	// a 5.
	public Iterator<Float> iterator() {
		return new ContinuousAttributeIterator(min, max, 5);
	}
}
