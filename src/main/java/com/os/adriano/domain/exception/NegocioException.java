package com.os.adriano.domain.exception;

public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) { //O erro recebido pelo NegocioException é sobrescrevendo a superclasse RuntimeException
		super(message);
	}
	
}
