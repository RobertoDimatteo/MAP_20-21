import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import keyboardinput.Keyboard;

/**
 * Classe tramite la quale il client contatta il server usando IP e numero di
 * porta su cui il server è in ascolto. Una volta instaurata la connessione il
 * client trasmette le sue richieste al server e ne aspetta la risposta.
 */
public class MainTestClient implements Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// MAIN

	/**
	 * Metodo main che crea l'oggetto &lt;InetAddress&gt; che modella l'indirizzo
	 * del server e l'oggetto &lt;socket&gt; che deve collegarsi a tale server.
	 * Inizializza i flussi di oggetti &lt;in&gt; e &lt;out&gt; per la trasmissione
	 * e ricezione di oggetti a e dal server.
	 * 
	 * Interagisce con l’utente mostrando tre opzioni: 
	 * 1) Nuova scoperta. 
	 * 2) Risultati in archivio.
	 * 3) Exit.
	 * 
	 * In entrambi i primi due casi trasmette la relativa richiesta e i necessari
	 * parametri al server e ne aspetta la risposta che sarà poi stampata a video.
	 * 
	 * @param args parametri passati in input al programma
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	@SuppressWarnings("removal")
	public static void main(String[] args) throws IOException {
		Socket socket;

		if (args.length != 0) {
			InetAddress addr = InetAddress.getByName(args[0]);
			System.out.println("addr = " + addr + "\nport =" + args[1]);
			socket = new Socket(addr, new Integer(args[1]));
		} else {
			InetAddress addr = InetAddress.getByName("127.0.0.1"); // indirizzo server locale
			int port = 8080;
			System.out.println("addr = " + addr + "\nport=" + port);
			socket = new Socket(addr, port);
		}
		System.out.println(socket);

		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		; // stream con richieste del client

		char risp = 's';
		do {
			System.out.println("Scegli una opzione:");
			int opzione;
			do {
				System.out.println("1: Nuova scoperta");
				System.out.println("2: Risultati in archivio");
				System.out.println("3: Exit");
				opzione = Keyboard.readInt();
			} while (opzione != 1 && opzione != 2 && opzione != 3);

			if (opzione == 3) {
				System.out.println("Grazie... Arrivederci");
				System.exit(0);
			}

			float minsup = 0f;
			float minGr = 0f;
			do {
				System.out.println("Inserire valore minimo supporto (minsup>0 e minsup<=1):");
				minsup = Keyboard.readFloat();
			} while (minsup <= 0 || minsup > 1);

			do {
				System.out.println("Inserire valore minimo grow rate (minGr>0):");
				minGr = Keyboard.readFloat();
			} while (minGr <= 0);

			try {
				System.out.println("Tabella target:");
				String targetName = Keyboard.readString();
				System.out.println("Tabella background:");
				String backgroundName = Keyboard.readString();
				String nameFile = targetName + "_" + backgroundName;
				if (targetName.equals("playtennistarget")
						|| targetName.equals("playtennisBackground") && backgroundName.equals("playtennisBackground")
						|| backgroundName.equals("playtennistarget")) {
					try {
						out.writeObject(opzione);
						out.writeObject(minsup);
						out.writeObject(minGr);
						out.writeObject(targetName);
						out.writeObject(backgroundName);
						out.writeObject(nameFile);
						String fpMiner = (String) (in.readObject());

						System.out.println("Frequent patterns");
						System.out.println(fpMiner);

						String epMiner = (String) (in.readObject());

						System.out.println("Emerging patterns");
						System.out.println(epMiner);
					} catch (IOException | ClassNotFoundException e) {
						System.err.println(e);
					}
				} else {
					throw new IOException();
				}
			} catch (IOException e) {
				System.err.println("Nomi tabelle inserite errati");
			}
			System.out.println("Vuoi ripetere?(s/n)");
			risp = Keyboard.readChar();
		} while (risp != 'n');
		out.writeObject(risp); // stringa inviata per far fermare l'esecuzione di un client
	}

}
