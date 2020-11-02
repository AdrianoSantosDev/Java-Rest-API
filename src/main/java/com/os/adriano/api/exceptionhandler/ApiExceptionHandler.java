package com.os.adriano.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import javax.websocket.OnMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Classe para tratar as exceptions vindas do controller

@ControllerAdvice //Tratamento do spring que trata os erros que vem do controller
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {//Personalizando erro
		
		var campos = new ArrayList<Problema.Campo>(); 	//Criando uma lista de campos
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) { //fazendo uma iteração sobre a lista de problemas e instanciando cada um com o nome e o problema.
			String nome = ((FieldError) error).getField(); //FieldError buscando o nome do problema
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos. "+
						   "Faça o preenchimento correto e tente novamente");
		problema.setDataHora(LocalDateTime.now()); //LocalDateTime.now() seta a data e hora do momento.
		problema.setCampos(campos);
				
		return super.handleExceptionInternal(ex, problema, headers, status, request); //handleExceptionInternal é uma exception interna para sobrescrever a superClasse handleMethodArgumentNotValid
	}
	
}
