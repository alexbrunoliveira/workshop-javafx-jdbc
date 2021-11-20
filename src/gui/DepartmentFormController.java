package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{
	
	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	
	private TextField txtId;
	
	@FXML	
	private TextField txtName;
	
	@FXML	
	private Label labelErrorName;
	
	@FXML	
	private Button btSave;
	
	@FXML	
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity= entity;
	}
	
	public void setDepartmentService (DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	
	@FXML	
	public  void onBtSaveAction(ActionEvent event) {
		
		// Sem frameWok � necess�rio a inser��o manual das exce��es
		
		if (entity == null) {
			throw new IllegalStateException("Entity eas null");
		}
		
		if (service == null) {
			throw new IllegalStateException("Service was Null");
		}
		
		try {
		entity = getFormData();
		// Salvando no banco de dados 
		service.saveOrUpdate(entity);
		notifyDataDataChangeListeners();
		// comando para fechar janela ap�s evento
		Utils.currentAge(event).close();
		
		}
		catch(DbException e) {
		Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataDataChangeListeners() {
		// Para cada DataChangeListener pertencente a lista dataChangeListeners
		// Calsse q realiza o evento
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		
		// convertendo para inteiro
		obj.setId(Utils.tryparseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML	
	public  void onBtCancelAction(ActionEvent event) {
		Utils.currentAge(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
	}
	
	// Inserindo restri��es
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
