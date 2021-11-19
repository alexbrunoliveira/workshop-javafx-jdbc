package gui;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	// Carregando dados Mockados

	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;

	private ObservableList<Department> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentAge(event);
		Department obj = new Department();
		// chamando método 
		creatDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}

	// Invertendo controle
	// injetando dependências princípio solid

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	// Iniciando o comportamento das colunas da tabela
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		// comando para tabela acompnhar o tamanho da janela

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	// método para acessar o serviço
	// carregar os departamentos
	// e joga para ObservableList

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// Carregando dados da lista
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

	private void creatDialogForm(Department obj,String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.updateFormData();
			
			// Carregando uma janela em frente a anterior
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			
			// comando que trava tela
			
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
	}
		catch (IOException e ) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
