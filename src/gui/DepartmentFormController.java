package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.exceptions.ValidationException;
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
		catch (ValidationException e ) {
			setErrorMessages(e.getErrors());
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
		
		ValidationException exception = new ValidationException("Validation error");
		
		// convertendo para inteiro
		obj.setId(Utils.tryparseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		// trim elimina espa�oes no inicio ou final do campo informado pelo usu�rio
		if (txtName.getText()==null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		
			
		// Verifica se houve um erro	
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
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
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}

}
