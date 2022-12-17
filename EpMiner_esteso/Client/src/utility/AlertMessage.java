package utility;

import javafx.scene.control.Alert;

/**
 * Classe contenente metodi per la gestione di finestre popup.
 */
public class AlertMessage {

	/**
	 * Apre una finestra popup con titolo, intestazione e contenuto dei parametri
	 * passati in input per la segnalazione di errori.
	 * 
	 * @param title       Titolo che comparirà nella finestra di popup
	 * @param headerText  Intestazione della finestra popup
	 * @param contentText Contenuto del testo della finestra popup
	 */
	public static void errorMessage(String title, String headerText, String contentText) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	/**
	 * Apre una finestra popup con titolo, intestazione e contenuto dei parametri
	 * passati in input per la segnalazione di warnings.
	 * 
	 * @param title       Titolo che comparirà nella finestra di popup
	 * @param headerText  Intestazione della finestra popup
	 * @param contentText Contenuto del testo della finestra popup
	 */
	public static void warningMessage(String title, String headerText, String contentText) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	/**
	 * Apre una finestra popup con titolo, intestazione e contenuto dei parametri
	 * passati in input per la visualizzazione di informazioni utili.
	 * 
	 * @param title       Titolo che comparirà nella finestra di popup
	 * @param headerText  Intestazione della finestra popup
	 * @param contentText Contenuto del testo della finestra popup
	 */
	public static void informationMessage(String title, String headerText, String contentText) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
