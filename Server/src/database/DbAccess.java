package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.InstanceNotFoundException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * 
 * @author Map Tutor
 *
 */
public class DbAccess {

	// ATTRIBUTI

	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"; // Per utilizzare questo Driver scaricare e
																			// aggiungere al classpath il connettore
																			// mysql connector)
	private final String DBMS = "jdbc:mysql"; // protocollo e sottoprotocollo
	private final String SERVER = "localhost"; // contiene l’identificativo del server su cui risiede la base di dati
	private final int PORT = 3306; // La porta su cui il DBMS MySQL accetta le connessioni
	private final String DATABASE = "Map"; // contiene il nome della base di dati
	private final String USER_ID = "Student"; // il nome dell’utente per l’accesso alla base di dati
	private final String PASSWORD = "map"; // contiene la password di autenticazione per l’utente identificato da
											// USER_ID

	private Connection conn; // gestisce una connessione

	// METODI

	// metodo che impartisce al class loader l’ordine di caricare il driver mysql,
	// inizializza la connessione riferita da conn
	public void initConnection() throws DatabaseConnectionException {
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID
				+ "&password=" + PASSWORD + "&serverTimezone=UTC";
		try {

			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}

		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);

		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}

	}

	// restituisce conn;
	public Connection getConnection() {
		return conn;
	}

	// chiude la connessione conn;
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}

}
