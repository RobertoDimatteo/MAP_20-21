package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

/**
 * Classe che avvia la prima scena dell'applicazione.
 */

public class App extends Application {

	/**
	 * Metodo per l'avvio della prima finestra dell'applicazione.
	 * 
	 * @param primaryStage stage iniziale dell'applicazione
	 * 
	 * @throws Exception lanciata per gestire una generica eccezione
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
			primaryStage.setTitle("EpMiner");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo main per l'avvio dell'applicazione.
	 * 
	 * @param args parametri passati in input al programma
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
