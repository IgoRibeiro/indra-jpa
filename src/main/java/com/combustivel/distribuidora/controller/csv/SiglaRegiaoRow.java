package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.SiglaRegiao;
import com.combustivel.distribuidora.repository.SiglaRegiaoRepository;

public class SiglaRegiaoRow {

	public List<SiglaRegiao> salvarRegistros(List<List<String>> registros, SiglaRegiaoRepository siglaRegiaoRepository) {
		//registros que serao salvos
		List<SiglaRegiao> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para SiglaRegiao
		registros.stream().forEach(registro->{
			
			String sigla = registro.get(0).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getSigla().equals(sigla)).count()==0L) {
				SiglaRegiao siglaRegiao = new SiglaRegiao();
				siglaRegiao.setSigla(sigla);
				rows.add(siglaRegiao);
			}
			
		});
		
		return siglaRegiaoRepository.saveAll(rows);
		
	}
	
}
