package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

// Metodo que acesa Stage onde o controller do evento está

public class Utils {
	public static Stage currentAge(ActionEvent event) {
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
}
