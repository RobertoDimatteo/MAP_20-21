package mining;

import data.Attribute;
import java.io.*;

/**
 * Classe astratta che modella un generico item che potrà essere di tipo
 * discreto o continuo (coppia attributo-valore).
 */
abstract class Item implements Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Attributo coinvolto nell'item.
	 */
	private Attribute attribute;

	/**
	 * Valore assegnato all'attributo.
	 */
	private Object value;

	// COSTRUTTORE

	/**
	 * Costruttore che inizializza i membri &lt;attribute&gt; e &lt;value&gt; con i
	 * valori passati come parametri.
	 * 
	 * @param attribute attributo coinvolto nell'item
	 * @param value     valore assegnato all'attributo
	 */
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;

	}

	// METODI

	/**
	 * Restituisce il membro &lt;attribute&gt;.
	 * 
	 * @return attributo coinvolto nell'item
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Restituisce il membro &lt;value&gt;.
	 * 
	 * @return valore assegnato all'attributo
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Metodo astratto per la verifica di un insieme di item. Realizzato nelle
	 * sottoclassi.
	 *
	 * @param value valore assegnato all'attributo
	 * 
	 * @return un valore booleano
	 */
	abstract boolean checkItemCondition(Object value);

	/**
	 * Restituisce una stringa nella forma &lt;attribute&gt;=&lt;value&gt;.
	 * 
	 * @return stringa concatenata
	 */
	public String toString() {
		return "(" + getAttribute() + "=" + getValue() + ")";
	}

}