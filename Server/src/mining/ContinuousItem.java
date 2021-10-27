package mining;

import data.ContinuousAttribute;

/**
 * Sottoclasse concreta che estende la classe astratta &lt;Item&gt; e modella la
 * coppia &lt;Attributo continuo - Intervallo di valori&gt;.
 */
public class ContinuousItem extends Item {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// COSTRUTTORE

	/**
	 * Costruttore che invoca il costruttore della superclasse astratta &lt;Item&gt;
	 * passandogli &lt;attribute&gt; e &lt;value&gt;.
	 * 
	 * @param attribute attributo continuo coinvolto nel continuous item
	 * @param value     valore che rappresenta un intervallo
	 */
	public ContinuousItem(ContinuousAttribute attribute, Interval value) {
		super(attribute, value);
	}

	// METODI

	/**
	 * Verifica che il parametro &lt;value&gt; rappresenti un numero reale incluso
	 * tra gli estremi dell’intervallo associato all' item.
	 * 
	 * @param value un valore
	 * 
	 * @return vero se il valore è incluso tra gli estremi dell'intervallo; falso
	 *         altrimenti.
	 */
	boolean checkItemCondition(Object value) {
		Interval i = (Interval) this.getValue();
		return i.checkValueInclusion((Float) value);
	}

	/**
	 * Avvalora la stringa che rappresenta lo stato dell’oggetto (per esempio,
	 * Temperatura in [10;20.4[) e ne restituisce il riferimento.
	 * 
	 * @return stringa che rappresenta lo stato dell’oggetto nella forma &lt;nome
	 *         attributo&gt; in [ &lt; inf&gt;,&lt;sup&gt;[
	 */
	public String toString() {
		String contAttr = "";
		Interval i = (Interval) this.getValue();
		return contAttr += "(" + this.getAttribute() + " in " + i + ")";
	}

}
