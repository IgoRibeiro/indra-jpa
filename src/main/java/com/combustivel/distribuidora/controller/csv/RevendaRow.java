package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Municipio;
import com.combustivel.distribuidora.entity.Revenda;
import com.combustivel.distribuidora.repository.RevendaRepository;

public class RevendaRow {

	public List<Revenda> salvarRegistros(List<List<String>> registros, RevendaRepository revendaRepository, List<Municipio> municipios) {
		
		//registros que serao salvos
		List<Revenda> rows = new ArrayList<>();
		try {
			
		
			//varrer registros e separar o que interessa para SiglaRegiao
			registros.stream().forEach(registro->{
				
				Long   idRevenda     = Long.valueOf(registro.get(registro.size()-7).trim());
				String nomeRevenda   = registro.get( 3).trim(); 
				String nomeMunicipio = registro.get( 2).trim(); 
				String siglaRegiao   = registro.get( 0).trim();
				String siglaEstado   = registro.get( 1).trim();
				
				//Detectei um problema nos nomes da revenda... Como a tabulacao do CSV sao dois espacos em branco ('  '), vi que existe nome da revenda com dois espacos em branco tbm. 
				//Nesse caso, vou tentar manter os nomes originais, concatenando as posicoes(colunas)
				if(registro.size()>11) {
					int qtdPosicoesRevenda = registro.size() - 11;
					for(int i=1; i<=qtdPosicoesRevenda; i++) {
						nomeRevenda += registro.get(3+i).trim();
					}
				}
				
				//se nao existir, adiciona row
				if(rows.stream().filter(r-> r.getId().equals(idRevenda)).count()==0L) {
					
					Municipio municipio = municipios.stream().filter(m-> m.getRegiao().getEstado().getSigla().equals(siglaEstado) && 
																		 m.getRegiao().getSiglaRegiao().getSigla().equals(siglaRegiao) &&
																		 m.getNome().equals(nomeMunicipio)).iterator().next();
					
					Revenda revenda = new Revenda();
					revenda.setId(idRevenda);
					revenda.setNome(nomeRevenda);
					revenda.setMunicipio(municipio);
					rows.add(revenda);
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return revendaRepository.saveAll(rows);
		
	}
	
}
