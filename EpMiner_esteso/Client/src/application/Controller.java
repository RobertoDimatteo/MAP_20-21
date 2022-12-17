package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che permette di spostarsi fra le varie finestre presenti
 * nell'interfaccia grafica.
 */
class Controller {

	// ATTRIBUTI

	private Stage stage;

	private Scene scene;

	private Parent root;

	// METODI

	/**
	 * Metodo che permette di visualizzare la schermata di Welcome dell'interfaccia.
	 * 
	 * @param event click del bottone "Indietro"
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	void scenaWelcome(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Metodo che permette di visualizzare la schermata di Program dell'interfaccia.
	 * 
	 * @param event click del bottone "Avvia"
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	void scenaProgram(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Program.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Metodo che permette di visualizzare la schermata di Info dell'interfaccia.
	 * 
	 * @param event click del bottone "Info"
	 * 
	 * @throws IOException lanciata per segnalare operazioni di I/O fallite o
	 *                     interrotte
	 */
	void scenaInfo(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Info.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}