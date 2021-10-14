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

// modella la comunicazione con un unico client.
public class ServerOneClient extends Thread implements Serializable{

	// ATTRIBUTI

	private Socket socket; // Terminale lato server del canale tramite cui avviene lo scambio di oggetti
							// client-server
	private ObjectInputStream in; // flusso di oggetti in input al server.
	private ObjectOutputStream out; // flusso di oggetti in output dal server al client.

	// COSTRUTTORE

	// Inizializza il membro this.socket con il parametro in input al costruttore.
	// Inizializza in e out, avvia il thread invocando il metodo start() (ereditato
	// da Thread).
	public ServerOneClient(Socket socket) throws IOException {

		this.socket = socket;

		in = new ObjectInputStream(socket.getInputStream());

		out = new ObjectOutputStream(socket.getOutputStream());

		start();
	}

	// METODI

	// Ridefinisce il metodo run della classe Thread (variazione funzionale).
	// Gestisce le richieste del client (apprendere pattern/regole e popolare con
	// queste archive; salvare archive in un file, avvalorare archive con oggetto
	// serializzato nel file)
	public void run() {

		try {
			while (true) {
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							EmergingPatternMiner epMiner = new EmergingPatternMiner(dataBackground, fpMiner, minGr);
							String epMinerStr = epMiner.toString();
							out.writeObject(epMinerStr);
							try {
								epMiner.salva(nameFile + minsup + "_minGr" + minGr + ".dat");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (IOException | EmptySetException e) {
							e.printStackTrace();

						}
					} catch (IOException | EmptySetException e) {
						e.printStackTrace();
					}
				} else {
					try {
						FrequentPatternMiner fpMiner = FrequentPatternMiner
								.carica(nameFile + minsup + ".dat");
						out.writeObject(fpMiner.toString());
						EmergingPatternMiner epMiner = EmergingPatternMiner
								.carica(nameFile + minsup + "_minGr" + minGr + ".dat");
						out.writeObject(epMiner.toString());
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					char risp = (char) in.readObject();
					if (risp == 'n')
						break;
				}
			}
		} catch (IOException | ClassNotFoundException | DatabaseConnectionException | SQLException
				| NoValueException e) {
			e.printStackTrace();
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
