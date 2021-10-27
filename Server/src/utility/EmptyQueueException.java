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
	 * Costruttore che stampa un messaggio di errore che avverte che la coda risulta
	 * vuota.
	 */
	public EmptyQueueException() {
		System.err.println("La coda è vuota");
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
