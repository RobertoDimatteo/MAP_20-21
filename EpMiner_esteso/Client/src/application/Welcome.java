package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.AlertMessage;

/**
 * Classe che mostra la prima schermata di Welcome dell'applicazione.
 */
public class Welcome {

	// ATTRIBUTI

	@FXML
	private Button avvia;

	@FXML
	private Button esci;

	@FXML
	private Button info;

	// METODI

	/**
	 * Avvia la connessione al Server, se va a buon fine mostra la schermata di
	 * Program dell'applicazione.
	 * 
	 * @param event click del bottone &lt;avvia&gt;
	 */
	@FXML
	void avviaProgramma(ActionEvent event) {
		try {
			Connection.connetti();
			Controller cs = new Controller();
			try {
				cs.scenaProgram(event);
			} catch (IOException e) {
			}
		} catch (IOException e) {
			AlertMessage.errorMessage("Error Dialog", "Connection error",
					"Errore. La connessione al server non è avvenuta. Dettagli errore: " + e.toString());
			return;
		}
	}

	/**
	 * Chiude l'applicazione.
	 * 
	 * @param event click del bottone &lt;esci&gt;
	 */
	@FXML
	void chiudiProgramma(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Mostra la schermata di Info dell'applicazione.
	 * 
	 * @param event click del bottone &lt;info&gt;
	 */
	@FXML
	void infoProgramma(ActionEvent event) {
		Controller cs = new Controller();
		try {
			cs.scenaInfo(event);
		} catch (IOException e) {
		}
	}

}