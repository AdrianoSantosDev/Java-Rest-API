package com.os.adriano.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.os.adriano.domain.model.Cliente;

//Nosso cliente repository extende da classe JpaRepository passando a Classe e o tipo primitivo do ID.
//Responsável por fazer as operações no banco de dados
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	//Podemos criar métodos de pesquisa
	
	List<Cliente> findByNome(String nome);
	Optional<Cliente> findById(Long id);
	List<Cliente> findByTelefone(String telefone);
	Cliente findByEmail(String email);
}
