package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Municipio;
import com.combustivel.distribuidora.entity.Regiao;
import com.combustivel.distribuidora.repository.MunicipioRepository;

public class MunicipioRow {

	public List<Municipio> salvarRegistros(List<List<String>> registros, MunicipioRepository municipioRepository, List<Regiao> regioes) {
		//registros que serao salvos
		List<Municipio> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para SiglaRegiao
		registros.stream().forEach(registro->{
			
			String nome = registro.get(2).trim(); 
			
			String sigla       = registro.get(0).trim();
			String siglaEstado = registro.get(1).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getRegiao().getEstado().getSigla().equals(siglaEstado) && 
									    r.getRegiao().getSiglaRegiao().getSigla().equals(sigla)  &&
									    r.getNome().equals(nome)).count()==0L) {
				
				Regiao regiao = regioes.stream().filter(f-> f.getEstado().getSigla().equals(siglaEstado) && f.getSiglaRegiao().getSigla().equals(sigla)).iterator().next();
				
				Municipio municipio = new Municipio();
				municipio.setRegiao(regiao);
				municipio.setNome(nome);
				rows.add(municipio);
			}
			
		});
		
		return municipioRepository.saveAll(rows);
		
	}
	
}
