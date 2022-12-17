package database;

/**
 * Classe che estende la classe &lt;Exception&gt; per modellare l’assenza di un valore
 * all’interno di un resultset.
 */
public class NoValueException extends Exception {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// COSTRUTTORE

	/**
	 * Costruttore che chiama il costruttore della superclasse &lt;Exception&gt;
	 * passandogli &lt;msg&gt;.
	 * 
	 * @param msg messaggio da inviare
	 */
	public NoValueException(String msg) {
		super(msg);
	}

}