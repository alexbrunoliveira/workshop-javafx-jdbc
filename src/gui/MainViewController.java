package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

// Inserindo itens de controle de tela

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuSellerAction");
	}
		
		@FXML
		public void onMenuItemDepartmentrAction() {
			System.out.println("onMenuDepartmentAction");
		}
			
			@FXML
			public void onMenuItemAboutAction() {
				System.out.println("onMenuAbouAction");
		
	}

		@Override
		public void initialize(URL uri, ResourceBundle rb) {
		
	}

}
