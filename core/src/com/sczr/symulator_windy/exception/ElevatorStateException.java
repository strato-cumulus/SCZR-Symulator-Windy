package com.sczr.symulator_windy.exception;

public class ElevatorStateException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ElevatorStateException() {
	}
	
	@Override
	public String getMessage() {
		return "Unsupported comnbination of states";
	}
	
}
