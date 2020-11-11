package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.UnidadeMedida;
import com.combustivel.distribuidora.repository.UnidadeMedidaRepository;

public class UnidadeMedidaRow {

	public List<UnidadeMedida> salvarRegistros(List<List<String>> registros, UnidadeMedidaRepository repository) {
		//registros que serao salvos
		List<UnidadeMedida> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para Bandeira
		registros.stream().forEach(registro->{
			
			String nome = registro.get(registro.size()-2).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getNome().equals(nome)).count()==0L) {
				UnidadeMedida entity = new UnidadeMedida();
				entity.setNome(nome);
				rows.add(entity);
			}
			
		});
		
		return repository.saveAll(rows);
		
	}
	
}
