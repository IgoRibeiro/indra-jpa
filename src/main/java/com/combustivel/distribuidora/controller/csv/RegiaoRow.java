package com.combustivel.distribuidora.controller.csv;

import java.util.ArrayList;
import java.util.List;

import com.combustivel.distribuidora.entity.Estado;
import com.combustivel.distribuidora.entity.Regiao;
import com.combustivel.distribuidora.entity.SiglaRegiao;
import com.combustivel.distribuidora.repository.RegiaoRepository;

public class RegiaoRow {

	public List<Regiao> salvarRegistros(List<List<String>> registros, RegiaoRepository regiaoRepository, List<Estado> estados, List<SiglaRegiao> siglasRegiao) {
		//registros que serao salvos
		List<Regiao> rows = new ArrayList<>();
		
		//varrer registros e separar o que interessa para SiglaRegiao
		registros.stream().forEach(registro->{
			
			String sigla       = registro.get(0).trim();
			String siglaEstado = registro.get(1).trim();
			
			//se nao existir, adiciona row
			if(rows.stream().filter(r-> r.getEstado().getSigla().equals(siglaEstado) && r.getSiglaRegiao().getSigla().equals(sigla)).count()==0L) {
				
				SiglaRegiao siglaRegiao = siglasRegiao.stream().filter(sr-> sr.getSigla().equals(sigla)).iterator().next();
				
				Estado estado = estados.stream().filter(sr-> sr.getSigla().equals(siglaEstado)).iterator().next();
				
				Regiao regiao = new Regiao();
				regiao.setSiglaRegiao(siglaRegiao);
				regiao.setEstado(estado);
				rows.add(regiao);
			}
			
		});
		
		return regiaoRepository.saveAll(rows);
		
	}
	
}
