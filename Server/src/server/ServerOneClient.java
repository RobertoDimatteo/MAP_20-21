package server;

import java.net.Socket;
import java.sql.SQLException;

import data.Data;
import data.EmptySetException;
import database.DatabaseConnectionException;
import database.NoValueException;
import mining.EmergingPatternMiner;
import mining.FrequentPatternMiner;

import java.io.*;

/**
 * Classe che modella la comunicazione con un unico client.
 */
public class ServerOneClient extends Thread implements Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Terminale lato server del canale tramite cui avviene lo scambio di oggetti
	 * client-server.
	 */
	private Socket socket;

	/**
	 * Flusso di oggetti in input al server.
	 */
	private ObjectInputStream in;

	/**
	 * Flusso di oggetti in output dal server al client.
	 */
	private ObjectOutputStream out;

	// COSTRUTTORE

	/**
	 * Costruttore che inizializza il membro &lt;this.socket&gt; con il parametro in
	 * input, inizializza &lt;in&gt; e &lt;out&gt; e avvia il thread invocando il
	 * metodo &lt;start()&gt; ereditato dalla classe &lt;Thread&gt;.
	 * 
	 * @param socket terminale lato server del canale
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	public ServerOneClient(Socket socket) throws IOException {

		this.socket = socket;

		in = new ObjectInputStream(socket.getInputStream());

		out = new ObjectOutputStream(socket.getOutputStream());

		start();
	}

	// METODI

	/**
	 * Gestisce le richieste del client: 
	 * 
	 * 1) apprendere pattern/regole e popolare con queste il pattern 
	 * 2) salvare il pattern in un file 
	 * 3) avvalorare il pattern con un oggetto serializzato nel file.
	 */
	public void run() {

		try {
			while (true) {
				char risp = (char) in.readObject();
				int opzione = (int) (in.readObject());
				float minsup = (float) (in.readObject());
				float minGr = (float) (in.readObject());
				String targetName = (String) (in.readObject());
				String backgroundName = (String) (in.readObject());
				String nameFile = (String) (in.readObject());

				if (opzione == 1) {

					Data dataTarget = new Data(targetName);
					Data dataBackground = new Data(backgroundName);

					try {
						FrequentPatternMiner fpMiner = new FrequentPatternMiner(dataTarget, minsup);
						String fpMinerStr = fpMiner.toString();
						out.writeObject(fpMinerStr);
						try {
							fpMiner.salva(nameFile + minsup + ".dat");
						} catch (IOException e1) {
							System.err.println(e1);
						}

						try {
							EmergingPatternMiner epMiner = new EmergingPatternMiner(dataBackground, fpMiner, minGr);
							String epMinerStr = epMiner.toString();
							out.writeObject(epMinerStr);
							try {
								epMiner.salva(nameFile + minsup + "_minGr" + minGr + ".dat");
							} catch (IOException e1) {
								System.err.println(e1);
							}
						} catch (IOException | EmptySetException e) {
							System.err.println(e);

						}
					} catch (IOException | EmptySetException e) {
						System.err.println(e);
					}
				} else if (opzione == 2) {
					try {
						FrequentPatternMiner fpMiner = FrequentPatternMiner.carica(nameFile + minsup + ".dat");
						out.writeObject(fpMiner.toString());
						EmergingPatternMiner epMiner = EmergingPatternMiner
								.carica(nameFile + minsup + "_minGr" + minGr + ".dat");
						out.writeObject(epMiner.toString());
					} catch (ClassNotFoundException | IOException e) {
						System.err.println(e);
					}
				}
				if (risp == 'n')
					break;
			}
		} catch (IOException | ClassNotFoundException | DatabaseConnectionException | SQLException
				| NoValueException e) {
			System.err.println(e);
		} finally {
			try {
				socket.close();
				System.out.println("Socket closed");
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}

	}

}
