package com.ncatz.yeray697.managepc.socket;

import java.awt.Color;

public enum Status {
	STARTING(Status.STARTING_MESSAGE),
	LISTENING(Status.LISTENING_MESSAGE),
	CONNECTED (Status.CONNECTED_MESSAGE),
	ERROR(Status.ERROR_MESSAGE);

	private static final String STARTING_MESSAGE = "Iniciando...";
	private static final String LISTENING_MESSAGE = "Esperando cliente...";
	private static final String CONNECTED_MESSAGE = "Conectado";
	private static final String ERROR_MESSAGE = "Ha ocurrido un error";
	
	public String text;
    public final Color color;

    Status(String text) {
        this.text = text;
        Color colorAux = Color.darkGray;
        if (text.equals(STARTING_MESSAGE)) {
        	colorAux = Color.darkGray;
        } else if (text.equals(LISTENING_MESSAGE)) {
        	colorAux = Color.darkGray;
        } else if (text.equals(CONNECTED_MESSAGE)) {
        	colorAux = Color.green;
        } else if (text.equals(ERROR_MESSAGE)) {
        	colorAux = Color.red;
        }
        this.color = colorAux;
    }
    
    Status(String text, Color color) {
        this.text = text;
        this.color = color;
    }

	public static Status customErrorStatus(String message) {
		Status status = Status.ERROR;
		status.text = message;
		return status;
	}

	public static Status customSuccessfulStatus(String message) {
		Status status = Status.CONNECTED;
		status.text = message;
		return status;
	}

	public static Status customInfoStatus(String message) {
		Status status = Status.LISTENING;
		status.text = message;
		return status;
	}

}
