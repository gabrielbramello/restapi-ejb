package br.com.ibm.restapi.util;

public class Util {

	public static boolean isObjetoTemValor(Object objeto) {
		return objeto != null && !objeto.toString().trim().isEmpty();
	}
}
