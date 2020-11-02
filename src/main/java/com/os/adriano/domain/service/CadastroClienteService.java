package com.os.adriano.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.adriano.domain.exception.NegocioException;
import com.os.adriano.domain.model.Cliente;
import com.os.adriano.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService { // Classe CadastroClienteService serve para ditar as regras de que para SALVAR OU 
									  // EXCLUIR algo tem que passar pelas regras do clienteRepository
									  // Criando esssa classe não tem como SALVAR OU EXCLUIR do controller direto para o repository 
									  // OS MÉTODOS DE ACESSO CONSEGUEM IR DIRETO DO CONTROLLER PARA O REPOSITORY
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail()); //Pegando o email do cliente recebido no método salvar e 
																					  //atribuindo a variável cliente existente
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
		}
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
