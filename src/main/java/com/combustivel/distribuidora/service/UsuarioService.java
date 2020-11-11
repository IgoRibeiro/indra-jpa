package com.combustivel.distribuidora.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.combustivel.distribuidora.entity.Usuario;
import com.combustivel.distribuidora.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsuarioService implements IUsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listar(){
		return Optional.ofNullable(usuarioRepository.findAll()).orElse(new ArrayList<Usuario>());
	}
	
    public Page<Usuario> listarPaginado(
    		Usuario usuario,
            int page,
            int size) {
    	
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");

    	Example<Usuario> example = Example.of(usuario, ExampleMatcher.matchingAll().withIgnoreCase());

    	return usuarioRepository.findAll(example, pageRequest);
    }
    
    public Optional<Usuario> procurarPorId(Long id) {
    	return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario entity) {
    	return usuarioRepository.save(entity);
    }
    
    public void excluir(Long id) {
    	usuarioRepository.delete(procurarPorId(id).get());
    }
    
}
