package utility;

/**
 * Classe che estende &lt;Exception&gt; per modellare la restituzione di una
 * coda vuota.
 */
public class EmptyQueueException extends Exception {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// COSTRUTTORE

	/**
	 * Costruttore.
	 */
	public EmptyQueueException() {
		
	}

	/**
	 * Costruttore che chiama il costruttore della superclasse &lt;Exception&gt;
	 * passandogli message.
	 * 
	 * @param message messaggio da stampare
	 */
	public EmptyQueueException(String message) {
		super(message);
	}

}
