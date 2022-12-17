package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.AlertMessage;

/**
 * Classe che gestisce la comunicazione tra client e server.
 */
public class Connection {

	// ATTRIBUTI

	/**
	 * Terminale lato client del canale tramite cui avviene lo scambio di oggetti
	 * client-server.
	 */
	private static Socket socket = null;

	/**
	 * Porta dell'host sulla quale è avviato il servizio.
	 */
	private static int PORT = 8080;

	private static InetAddress addr;

	/**
	 * Flusso di input della socket.
	 */
	private static ObjectInputStream in;

	/**
	 * Flusso di output della socket.
	 */
	private static ObjectOutputStream out;

	/**
	 * Risultato finale della comunicazione col server.
	 */
	private String risultato = new String();

	// COSTRUTTORE

	/**
	 * Costruttore della classe &lt;Connection&gt;.
	 */
	public Connection() {

	}

	// METODI

	/**
	 * Costruisce una nuova Socket per comunicare con il Server.
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	static void connetti() throws IOException {

		if (socket != null && !socket.isClosed())
			return; // Se la socket è già settata

		try {
			addr = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			AlertMessage.errorMessage("Error Dialog", "Generic error",
					"Errore. L'indirizzo IP dell'host non può essere determinato. Dettagli errore: " + e.toString());
			return;
		}

		socket = new Socket(addr, PORT);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Chiude la Socket nel caso sia aperta.
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	static void disconnetti() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
	}

	/**
	 * Comunica con il Server al fine di ottenere i pattern frequenti e i pattern
	 * emergenti.
	 * 
	 * @param targetName     Nome della tabella Target
	 * @param backgroundName Nome della tabella Background
	 * @param opzione        Valore che indica il servizio scelto: 1) Nuova scoperta
	 *                       2) Risultati in archivio
	 * @param minsup         Valore numerico rappresentante il minimo supporto
	 * @param minGr          Valore numerico rappresentante il minimo growrate
	 * @param nameFile       Nome del file da utilizzare per leggere/scrivere i
	 *                       risultati
	 * @return stringa rappresentante i pattern frequenti ed emergenti
	 */
	String elaboraRichiesta(String targetName, String backgroundName, int opzione, float minsup, float minGr,
			String nameFile) {
		if (targetName.equals("playtennistarget")
				|| targetName.equals("playtennisBackground") && backgroundName.equals("playtennisBackground")
				|| backgroundName.equals("playtennistarget")) {
			try {
				out.writeObject('s');
				out.writeObject(opzione);
				out.writeObject(minsup);
				out.writeObject(minGr);
				out.writeObject(targetName);
				out.writeObject(backgroundName);
				out.writeObject(nameFile);
				StringBuilder s = new StringBuilder();
				String titoloFP = "Frequent Patterns:\n";
				String titoloEP = "\nEmerging Patterns:\n";
				String fpMiner = (String) in.readObject();
				String epMiner = (String) in.readObject();
				s.append(titoloFP);
				s.append(fpMiner);
				s.append(titoloEP);
				s.append(epMiner);
				this.risultato = s.toString();
			} catch (IOException | ClassNotFoundException e) {
				AlertMessage.warningMessage("Warning Dialog", "Generic warning",
						"Warning. Impossibile elaborare la richiesta.\nAssicurarsi che il server sia acceso e che i campi del form siano tutti riempiti."
								+ e.toString());
			}
		} else {
			AlertMessage.warningMessage("Warning Dialog", "Generic warning", "Warning. Nomi delle tabelle errati.");
		}
		return this.risultato;
	}

}
