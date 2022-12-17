package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utility.AlertMessage;

/**
 * Classe che rappresenta le azioni presenti sulla scena Program.
 */
public class Program {

	// ATTRIBUTI

	@FXML
	private Button richiesta;

	@FXML
	private Button help;

	@FXML
	private Button cancella;

	@FXML
	private Button esci;

	@FXML
	private Button indietro;

	@FXML
	private RadioButton nuovo;

	@FXML
	private RadioButton archivio;

	@FXML
	private TextArea risultati;

	@FXML
	private TextField tabellaBackground;

	@FXML
	private TextField tabellaTarget;

	@FXML
	private TextField minGr;

	@FXML
	private TextField minsup;

	// METODI

	/**
	 * Invia una richiesta al Server con tutte le informazioni utili per la ricerca
	 * dei pattern frequenti ed emergenti. Stampa all'interno della TextArea il
	 * risultato della richiesta.
	 * 
	 * @param event click del bottone &lt;richiesta&gt;
	 */
	@FXML
	void elaboraRichiesta(ActionEvent event) {
		String target = this.tabellaTarget.getText();
		String background = this.tabellaBackground.getText();
		String supporto = this.minsup.getText();
		String growrate = this.minGr.getText();

		try {
			if (target.equals("") || background.equals("") || supporto.equals("") || growrate.equals("")) {
				AlertMessage.warningMessage("Warning Dialog", "Generic warning",
						"Warning. Inserire tutti i dati nei campi.");
			} else if (!this.archivio.isSelected() && !this.nuovo.isSelected()) {
				AlertMessage.warningMessage("Warning Dialog", "Generic warning",
						"Warning. Scegliere fra Nuova Scoperta o Risultati in archivio.");
			} else {
				String file = this.tabellaTarget.getText() + "_" + this.tabellaTarget.getText();
				String esito = new String();
				Float f_sup = Float.valueOf(Float.parseFloat(supporto));
				Float f_grow = Float.valueOf(Float.parseFloat(growrate));
				if (f_sup.floatValue() <= 0.0F || f_sup.floatValue() > 1.0F || f_grow.floatValue() <= 0.0F) {
					AlertMessage.warningMessage("Warning Dialog", "Generic warning",
							"Warning. Valori di minimo supporto o growrate non validi.");
				} else {
					Connection c = new Connection();
					esito = c.elaboraRichiesta(target, background, opzione(), f_sup.floatValue(), f_grow.floatValue(),
							file);
					this.risultati.setText(esito);
				}
			}
		} catch (NumberFormatException e) {
			AlertMessage.errorMessage("Error Dialog", "Generic error",
					"Errore. Impossibile elaborare la richiesta. Formato minimo supporto o minimo growrate non valido. Inserire valore reale.");
		} catch (Exception e) {
			AlertMessage.errorMessage("Error Dialog", "Generic error", "Errore. Impossibile elaborare la richiesta.");
		}
	}

	/**
	 * Cancella il contenuto delle TextField e della TextArea.
	 * 
	 * @param event click del bottone &lt;cancella&gt;
	 */
	@FXML
	void cancellaCampi(ActionEvent event) {
		this.tabellaTarget.setText("");
		this.tabellaBackground.setText("");
		this.minGr.setText("");
		this.minsup.setText("");
		this.risultati.setText("");
	}

	/**
	 * Mostra le informazioni utili per un corretto completamento dei campi tramite
	 * l'uso di un popup di informazione.
	 * 
	 * @param event click del bottone &lt;help&gt;
	 */
	@FXML
	void help(ActionEvent event) {
		AlertMessage.informationMessage("Information Dialog", "Info",
				"Si ricorda che per poter elaborare una richiesta bisogna rispettare i seguenti vincoli: \n"
						+ "- Non inserire caratteri speciali nei nomi delle tabelle.\n"
						+ "- Il minimo supporto deve essere compreso fra 0 e 1 (estremo sinistro escluso).\n"
						+ "- Il minimo growrate deve essere maggiore di 0.\n\n"
						+ "- Nel caso in cui il Server venga spento dopo aver "
						+ "già effettuato una prima richiesta e se ne voglia effettuare un'altra, seguire questi precisi passaggi:\n"
						+ "1. Premere Indietro\n" + "2. Accendere il Server\n" + "3. Premere Avvia");
	}

	/**
	 * Seleziona l'opzione "Nuova Scoperta" e cancella la spunta dall'opzione
	 * "Risultati in Archivio".
	 * 
	 * @param event spunta dell'opzione "Nuova Scoperta"
	 */
	@FXML
	void nuovoSelezionato(ActionEvent event) {
		this.archivio.setSelected(false);
	}

	/**
	 * Seleziona l'opzione "Risultati in Archivio" e cancella la spunta dall'opzione
	 * "Nuova Scoperta".
	 * 
	 * @param event spunta dell'opzione "Risultati in Archivio"
	 */
	@FXML
	void archivioSelezionato(ActionEvent event) {
		this.nuovo.setSelected(false);
	}

	/**
	 * Traduce il risultato dell'opzione presente nella radio button in un intero.
	 * 
	 * @return intero rappresentante la tipologia di servizio da chiedere al Server.
	 */
	private int opzione() {
		if (this.nuovo.isSelected())
			return 1;
		return 2;
	}

	/**
	 * Mostra la schermata di Welcome dell'interfaccia.
	 * 
	 * @param event click del bottone &lt;indietro&gt;
	 */
	@FXML
	void tornaIndietro(ActionEvent event) {
		try {
			Connection.disconnetti();
			Controller cs = new Controller();
			try {
				cs.scenaWelcome(event);
			} catch (IOException e) {
			}
		} catch (IOException e) {
			AlertMessage.errorMessage("Error Dialog", "Connection error",
					"Errore. La disconnessione al server non è avvenuta. Dettagli errore: " + e.toString());
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

}
