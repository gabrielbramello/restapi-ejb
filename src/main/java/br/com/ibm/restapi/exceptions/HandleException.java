package br.com.ibm.restapi.exceptions;

import java.util.ArrayList;
import java.util.List;

public class HandleException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	
	public HandleException(String mensagem, int codigo) {
		super(mensagem);
		this.codigo=codigo;	
	}

	public int getCodigo() {
		return codigo;
	}
	
	
	
	

}
