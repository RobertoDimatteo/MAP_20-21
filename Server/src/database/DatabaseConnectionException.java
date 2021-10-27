package database;

/**
 * Classe che estende la classe &lt;Exception&gt; per modellare l'eventuale
 * fallimento di una connessione al database.
 */
public class DatabaseConnectionException extends Exception {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// COSTRUTTORE

	/**
	 * Costruttore che chiama il costruttore della superclasse &lt;Exception&gt;
	 * passandogli &lt;msg&gt;
	 * 
	 * @param msg messaggio da inviare
	 */
	DatabaseConnectionException(String msg) {
		super(msg);
	}

}