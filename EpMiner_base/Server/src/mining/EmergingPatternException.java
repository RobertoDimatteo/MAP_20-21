package mining;

/**
 * Classe che estende la classe &lt;Exception&gt; per gestire l'eventuale
 * fallimento nei confronti delle condizioni sul minimo growrate.
 */
public class EmergingPatternException extends Exception {

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore.
	 */
	public EmergingPatternException() {
		
	}

	/**
	 * Costruttore che chiama il costruttore della superclasse &lt;Exception&gt;
	 * passandogli message.
	 * 
	 * @param message messaggio da stampare
	 */
	public EmergingPatternException(String message) {
		super(message);
	}

}
