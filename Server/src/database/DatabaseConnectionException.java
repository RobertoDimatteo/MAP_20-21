package database;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception {
	DatabaseConnectionException(String msg) {
		super(msg);
	}
}
