package com.porfirio.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Constants {
	public static final int INSERT = getValue("insert");
	public static final int UPDATE = getValue("update");
	
	private static int getValue(String operation) throws RuntimeException{
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config");
			return Integer.parseInt(rb.getString(operation));
		} catch (MissingResourceException ex) {
			throw new RuntimeException("Archivo de configuración no encontrado");
		} catch (NumberFormatException e) {
			throw new RuntimeException("La opción proporcionada no es válida");
		}
		
	}
}
