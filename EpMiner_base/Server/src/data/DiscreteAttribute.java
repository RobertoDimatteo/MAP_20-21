package data;

/**
 * Sottoclasse concreta che estende la classe astratta &lt;Attribute&gt; e modella un
 * attributo discreto rappresentando l’insieme di valori distinti del relativo
 * dominio.
 */
public class DiscreteAttribute extends Attribute {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array di stringhe che rappresenta i valori che può assumere un attributo. Ad
	 * ogni stringa viene associato un valore discreto.
	 */
	private String values[];

	// COSTRUTTORE

	/**
	 * Costruttore che invoca il costruttore della classe madre &lt;Attribute&gt; passandogli
	 * &lt;name&gt; e &lt;index&gt; e inizializza il membro l'array &lt;values[]&gt; con i valori
	 * discreti passati come parametro.
	 * 
	 * @param name   nome dell'attributo
	 * @param index  valore dell'identificativo numerico dell'attributo
	 * @param values valori discreti che ne costituiscono il dominio
	 */
	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);

		this.values = values;
	}

	// METODI

	/**
	 * Restituisce la cardinalità del membro &lt;values[]&gt;.
	 * 
	 * @return numero di valori discreti dell'attributo
	 */
	public int getNumberOfDistinctValues() {
		return values.length;
	}

	/**
	 * Restituisce il valore in posizione &lt;index&gt; del membro &lt;values[]&gt;.
	 * 
	 * @param index identificativo numerico dell'attributo
	 * 
	 * @return un valore nel dominio dell’attributo
	 */
	public String getValue(int index) {
		return values[index];
	}

	/**
	 * Restituisce l'array di stringhe &lt;values[]&gt;.
	 * 
	 * @return array di stringhe che rappresenta i valori che può assumere un
	 *         attributo
	 */
	public String[] getValues() {
		return values;
	}

}