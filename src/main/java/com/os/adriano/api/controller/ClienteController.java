package com.os.adriano.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.os.adriano.domain.model.Cliente;
import com.os.adriano.domain.repository.ClienteRepository;
import com.os.adriano.domain.service.CadastroClienteService;

@RestController //Faz o spring configurar essa classe para o GETMAPPING /clientes funcionar
@RequestMapping("/clientes") 
public class ClienteController {
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//Adicionar o DEVTOOLS para atualizar a api sem precisar parar e subir novamente	
	//URL do serviço /clientes/verbo http: GET (ASSIM QUE BATER NESSA URL ENTRA NO MÉTODO LISTAR)
	@GetMapping
	public List<Cliente> listar() { //Busca todos os clientes
		return clienteRepository.findAll();
//		//Arrays.asList cria uma lista e adiciona os objetos passados
//		return Arrays.asList(cliente1, cliente2, cliente3);
	}

	@GetMapping("/{clienteId}") //Busca o cliente pelo ID
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	// Cadastrando Clientes
	@PostMapping //Método para enviar 
	@ResponseStatus(HttpStatus.CREATED) //Código de status correto para criação de um novo objeto
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) { //@RequestBody adiciona o que for passado no corpo da requisição dentro de cliente / @Valid insere as validações feitas na classe cliente.
		return cadastroCliente.salvar(cliente); //Salva o cliente e retorna no corpo da requisição
	}
	// Alterando Clientes
	
	@PutMapping("/{clienteId}") //Método para alteração
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, 
			@RequestBody Cliente cliente){ //ResponseEntity<Cliente> RETORNO DO MÉTODO / ATUALIZAR NOME DO MÉTODO / @PathVariable VAI VINCULAR O clienteId QUE VEM DA URL COM O clienteId QUE O MÉTODO RECEBE.
		if (!clienteRepository.existsById(clienteId)) { //SE O CLIENTEID NÃO EXISTIR RETORNA UM ERRO NOT FOUND
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId); //ALTERANDO OS DADOS DO CLIENTE, SEM ATUALIZAR O ID, cliente ESSE VEIO DO REQUESTBODY
		cliente = cadastroCliente.salvar(cliente); //SALVANDO OS DADOS
		
		return ResponseEntity.ok(cliente);
	}
	
	//Método para remoção
	@DeleteMapping("/{clienteId}") //EndPoint para a remoção passando o clienteId
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){ //ResponseEntity<Void> tratamento do retorno da requisição, VOID não retorna corpo.
		if(!clienteRepository.existsById(clienteId)) { //Verificação se o clienteId existe se não, devolve um erro
			return ResponseEntity.notFound().build();
		}
		cadastroCliente.excluir(clienteId); //Cliente Repository faz a comunicação com o banco, e deleta o cliente pelo id solicitado
		return ResponseEntity.noContent().build(); //Retorno noContent é passado como código de status 0 204 que é mais específico
	}
}
