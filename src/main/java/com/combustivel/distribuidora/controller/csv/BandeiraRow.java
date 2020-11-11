package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.repository.BandeiraRepository;

public class BandeiraRow {

	public List<Bandeira> salvarRegistros(List<List<String>> registros, BandeiraRepository bandeiraRepository) {
		//registros que serao salvos
		List<Bandeira> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para Bandeira
		registros.stream().forEach(registro->{
			
			String nome = registro.get(registro.size()-1);
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getNome().equals(nome)).count()==0L) {
				Bandeira bandeira = new Bandeira();
				bandeira.setNome(nome);
				rows.add(bandeira);
			}
			
		});
		
		return bandeiraRepository.saveAll(rows);
		
	}
	
}
