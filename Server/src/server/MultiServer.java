package server;

import java.io.*;
import java.net.*;

/**
 * Classe che modella un server in grado di accettare le richieste trasmesse da
 * un generico client e istanzia un oggetto della classe &lt;ServerOneClient&gt;
 * che si occuperà di servire le richieste in un thread dedicato. Il server sarà
 * registrato su una porta predefinita (al di fuori del range 1-1024).
 */
public class MultiServer {

	// ATTRIBUTI

	/**
	 * Numero di porta su cui il server è in ascolto.
	 */
	public int PORT = 8080;

	/**
	 * Costruttore che inizializza il numero di porta (default 8080) e invoca il
	 * metodo &lt;run()&gt;;
	 * 
	 * @param port numero di porta
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	public MultiServer(int port) throws IOException {
		this.PORT = port;
		run();
	}

	/**
	 * Assegna ad una variabile locale &lt;s&gt; il riferimento ad una istanza della
	 * classe &lt;ServerSocket&gt; creata usando la porta &lt;PORT&gt;, la quale poi
	 * si pone in attesa di richieste di connessione da parte del client in risposta
	 * alle quali viene restituito l’oggetto &lt;Socket&gt; da passare come
	 * argomento al costruttore della classe &lt;ServerOneClient&gt;.
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	private void run() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Avviato");

		try {
			while (true) {
				// Si blocca finchè non si verifica una connessione:
				Socket socket = s.accept();
				try {
					System.out.println("Connessione di " + socket);
					System.out.println("Nuovo client connesso");
					new ServerOneClient(socket);
				} catch (IOException e) {
					// Se fallisce chiude il socket,
					// altrimenti il thread la chiuderà:
					System.out.println("closing...");
					socket.close();
				}
			}
		} finally {
			s.close();
		}

	}

}
