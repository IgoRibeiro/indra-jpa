package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Produto;
import com.combustivel.distribuidora.entity.UnidadeMedida;
import com.combustivel.distribuidora.repository.ProdutoRepository;

public class ProdutoRow {

	public List<Produto> salvarRegistros(List<List<String>> registros, ProdutoRepository produtoRepository, List<UnidadeMedida> unidadesMedida) {
		//registros que serao salvos
		List<Produto> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para Bandeira
		registros.stream().forEach(registro->{
			
			String unidade = registro.get(registro.size()-2).trim();
			String nome    = registro.get(registro.size()-6).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getUnidadeMedida().getNome().equals(unidade) && r.getNome().equals(nome)).count()==0L) {
				
				UnidadeMedida unidadeMedida = unidadesMedida.stream().filter(um-> um.getNome().equals(unidade)).iterator().next();
				
				Produto produto = new Produto();
				produto.setUnidadeMedida(unidadeMedida);
				produto.setNome(nome);
				rows.add(produto);
			}
			
		});
		
		return produtoRepository.saveAll(rows);
		
	}
	
}
