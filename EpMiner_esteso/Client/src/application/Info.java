package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe che mostra informazioni all'uso dell'applicazione.
 */
public class Info {

	// ATTRIBUTI

	@FXML
	private Button indietro;

	// METODI

	/**
	 * Mostra la schermata di Welcome dell'interfaccia.
	 * 
	 * @param event click del bottone &lt;indietro&gt;
	 */
	@FXML
	void tornaIndietro(ActionEvent event) {
		Controller cs = new Controller();
		try {
			cs.scenaWelcome(event);
		} catch (IOException e) {

		}

	}

}