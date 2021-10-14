package server;

import java.io.*;
import java.net.*;

//modella un server in grado di accettare la richiesta trasmesse 
//da un generico Client e istanzia un oggetto della classe ServerOneClient 
//che si occupera di servire le richieste del client in un thred dedicato. 
//Il Server sarà registrato su una porta predefinita (al di fuori del range 1-1024), per esempio 8080.
public class MultiServer {

	// ATTRIBUTI

	public static final int PORT = 8080;

	// invoca il metodo privato run.
	public MultiServer() throws IOException {
		run();
	}

	// crea un oggetto istanza di MultiServer.
	public static void main(String[] args) throws IOException {
		new MultiServer();

	}

	// assegna ad una variabile locale s il riferimento ad una istanza della classe
	// ServerSocket creata usando la porta PORT. s si pone in attesa di richieste di
	// connessione da parte di client in risposta alle quali viene restituito
	// l’oggetto Socket da passare come argomento al costruttore della classe
	// ServerOneClient.
	private void run() throws IOException { // DA RIVEDERE
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
