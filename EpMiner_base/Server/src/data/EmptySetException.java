package data;

/**
 * Classe che estende la classe &lt;Exception&gt; per modellare l'eccezione che
 * occorre qualora l'insieme di training fosse vuoto.
 */
public class EmptySetException extends Exception {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// COSTRUTTORI

	/**
	 * Costruttore.
	 */
	public EmptySetException() {
		
	}

	/**
	 * Costruttore che chiama il costruttore della superclasse &lt;Exception&gt;
	 * passandogli &lt;message&gt;.
	 * 
	 * @param message messaggio da inviare
	 */
	public EmptySetException(String message) {
		super(message);
	}

}