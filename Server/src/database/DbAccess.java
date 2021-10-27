package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.InstanceNotFoundException;

/**
 * Classe che gestisce l'accesso al database per la lettura dei dati di
 * training.
 * 
 * @author Map Tutor
 *
 */
public class DbAccess {

	// ATTRIBUTI

	/**
	 * Driver.
	 */
	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	/**
	 * Protocollo e sottoprotocollo.
	 */
	private final String DBMS = "jdbc:mysql";

	/**
	 * Identificativo del server su cui risiede il database.
	 */
	private final String SERVER = "localhost";

	/**
	 * La porta su cui il DBMS MySQL accetta le connessioni.
	 */
	private final int PORT = 3306;
 
	/**
	 * Nome del database a cui connettersi.
	 */
	private final String DATABASE = "Map";

	/**
	 * Nome dell'utente per l'accesso al database.
	 */
	private final String USER_ID = "Student";

	/**
	 * Password di autenticazione per l'utente identificato da &lt;USER_ID&gt;.
	 */
	private final String PASSWORD = "map";

	/**
	 * Gestisce una connessione.
	 */
	private Connection conn;

	// METODI

	/**
	 * Impartisce al class loader l’ordine di caricare il driver MYSQL
	 * e inizializza la connessione riferita da &lt;conn&gt;.
	 * 
	 * @throws DatabaseConnectionException lanciata quando fallisce la connessione
	 *                                     al database
	 */
	@SuppressWarnings("deprecation")
	public void initConnection() throws DatabaseConnectionException {
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID
				+ "&password=" + PASSWORD + "&serverTimezone=UTC";

		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		} catch (InstantiationException e) {
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

	/**
	 * Restituisce la connessione &lt;conn&gt;.
	 * 
	 * @return connessione
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Chiude la connessione &lt;conn&gt;.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}

}