package data;

import java.io.*;

/**
 * Classe astratta che modella un generico attributo discreto o continuo.
 */
public abstract class Attribute implements Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nome simbolico dell'attributo.
	 */
	private String name;

	/**
	 * Identificativo numerico dell'attributo che rappresenta l’attributo nella
	 * tabella di dati.
	 */
	private int index;

	// COSTRUTTORE

	/**
	 * Costruttore che inizializza i membri &lt;name&gt; e &lt;index&gt;
	 * con i valori passati come parametri.
	 * 
	 * @param name  nome dell'attributo
	 * @param index valore dell'identificativo numerico dell'attributo
	 */
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// METODI

	/**
	 * Restituisce il valore del membro &lt;name&gt;.
	 * 
	 * @return nome dell'attributo
	 */
	public String getName() {
		return name;
	}

	/**
	 * Restituisce il valore del membro &lt;index&gt;.
	 * 
	 * @return identificativo numerico dell'attributo
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Restituisce il valore dell'attributo &lt;name&gt;.
	 * 
	 * @return nome dell'attributo
	 */
	public String toString() {
		return this.name;
	}

}