package mining;

import data.DiscreteAttribute;

/**
 * Sottoclasse concreta che estende la classe astratta &lt;Item&gt; e
 * rappresenta la coppia &lt;Attributo discreto - Valore discreto&gt;.
 */
class DiscreteItem extends Item {

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
	 * @param attribute attributo discreto coinvolto nel discrete item
	 * @param value     stringa che rappresenta un valore
	 */
	public DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	// METODI

	/**
	 * Verifica che il membro &lt;value&gt; sia uguale (nello stato) all’argomento
	 * passato come parametro della funzione.
	 * 
	 * @param value un valore
	 * 
	 * @return vero se il valore è uguale nello stato all'argomento passato come
	 *         parametro della funzione; falso altrimenti.
	 */
	protected boolean checkItemCondition(Object value) {
		return getValue().equals(value);
	}

}
