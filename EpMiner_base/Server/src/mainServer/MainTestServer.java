package mainServer;

import java.io.IOException;

import server.MultiServer;

/**
 * Classe che rappresenta il main del server, il quale costruisce oggetti della
 * classe &lt;MultiServer&gt; per gestire le richieste da parte del client.
 */
public class MainTestServer {

	/**
	 * Metodo main che crea l'oggetto istanza della classe &lt;MultiServer&gt;
	 * utilizzando il numero di porta di default (8080).
	 * 
	 * @param args Parametri passati in input al programma
	 */
	public static void main(String[] args) {
		try {
			new MultiServer(8080);

		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

}