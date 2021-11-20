package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	/** Atributo para carregar v�rios erros q podem ocorrer na exce��o
	    O primeiro String � para o campo e o segundo para o erro
	*/
	private Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	// Retornando os erros
	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}
