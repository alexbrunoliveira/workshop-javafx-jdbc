package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// instanciando o objeto fxmlLoader
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();
			
			// Comando que faz menu bar preencher a tela
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			// objeto criado do tipo Scene
			Scene mainScene = new Scene(scrollPane);
			
			// cena principal setada
			
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			
			// mostrando palco
			
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
