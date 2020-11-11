package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Estado;
import com.combustivel.distribuidora.repository.EstadoRepository;

public class EstadoRow {

	public List<Estado> salvarRegistros(List<List<String>> registros, EstadoRepository estadoRepository) {
		//registros que serao salvos
		List<Estado> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para Bandeira
		registros.stream().forEach(registro->{
			
			String sigla = registro.get(1).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getSigla().equals(sigla)).count()==0L) {
				Estado estado = new Estado();
				estado.setSigla(sigla);
				rows.add(estado);
			}
			
		});
		
		return estadoRepository.saveAll(rows);
		
	}
	
}
