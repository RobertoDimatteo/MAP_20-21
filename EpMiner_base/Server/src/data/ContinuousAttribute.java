package data;

import java.util.Iterator;

/**
 * Sottoclasse concreta che estende la classe astratta &lt;Attribute&gt; e
 * modella un attributo numerico rappresentandone il dominio [min, max].
 */
public class ContinuousAttribute extends Attribute implements Iterable<Float> {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Estremo inferiore dell'intervallo.
	 */
	private float min;

	/**
	 * Estremo superiore dell'intervallo.
	 */
	private float max;

	// COSTRUTTORE

	/**
	 * Costruttore che invoca il costruttore della classe madre &lt;Attribute&gt;
	 * passandogli &lt;name&gt; e &lt;index&gt; e inizializza i membri &lt;min&gt; e
	 * &lt;max&gt; con i valori passati come parametri.
	 * 
	 * @param name  nome dell'attributo
	 * @param index valore dell'identificativo numerico dell'attributo
	 * @param min   valore dell'estremo inferiore dell'intervallo
	 * @param max   valore dell'estremo superiore dell'intervallo
	 */
	public ContinuousAttribute(String name, int index, float min, float max) {
		super(name, index);

		this.min = min;
		this.max = max;
	}

	// METODI

	/**
	 * Restituisce il valore del membro &lt;min&gt;.
	 * 
	 * @return estremo inferiore dell'intervallo
	 */
	public float getMin() {
		return min;
	}

	/**
	 * Restituisce il valore del membro &lt;max&gt;.
	 * 
	 * @return estremo superiore dell'intervallo
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Istanzia e restituisce un riferimento ad un oggetto della classe
	 * &lt;ContinuousAttributeIterator&gt; con numero di intervalli di
	 * discretizzazione pari a 5.
	 */
	public Iterator<Float> iterator() {
		return new ContinuousAttributeIterator(min, max, 5);
	}

}