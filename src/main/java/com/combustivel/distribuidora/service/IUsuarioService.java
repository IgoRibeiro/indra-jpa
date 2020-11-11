package com.combustivel.distribuidora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.combustivel.distribuidora.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> listar();
	
    public Page<Usuario> listarPaginado(
    		Usuario usuario,
            int page,
            int size);
    
    public Optional<Usuario> procurarPorId(Long id);

    public Usuario salvar(Usuario entity) ;
    
    public void excluir(Long id) ;
    
}
