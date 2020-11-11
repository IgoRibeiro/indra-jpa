package com.combustivel.distribuidora.controller.csv;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;
import com.combustivel.distribuidora.entity.Produto;
import com.combustivel.distribuidora.entity.Revenda;
import com.combustivel.distribuidora.repository.HistoricoPrecoCombustivelRepository;

public class HistoricoPrecoCombustivelRow {

	public List<HistoricoPrecoCombustivel> salvarRegistros (
			List<List<String>> registros, 
			HistoricoPrecoCombustivelRepository historicoPrecoCombustivelRepository,
			List<Revenda>  revendas,
			List<Produto>  produtos,
			List<Bandeira> bandeiras) throws Exception {
		
		//registros que serao salvos
		List<HistoricoPrecoCombustivel> rows = new ArrayList<>();
		List<String> teste = new ArrayList<>();
		try {
			
			//varrer registros e separar o que interessa para SiglaRegiao
			registros.stream().forEach(registro->{
				teste.clear();
				teste.add(registro.toString());
				Long   idRevenda     = Long.valueOf(registro.get(registro.size()-7).trim());
				String nomeProduto   = registro.get(registro.size()-6).trim();
				String dataColeta    = registro.get(registro.size()-5).trim(); 
				String valCompra     = registro.get(registro.size()-4).trim(); 
				String valVenda      = registro.get(registro.size()-3).trim(); 
				String unidade       = registro.get(registro.size()-2).trim();
				String nomeBandeira  = registro.get(registro.size()-1).trim();

				Revenda  revenda  = revendas .stream().filter(rev->rev.getId().equals(idRevenda)).iterator().next();
				Produto  produto  = produtos .stream().filter(prod->prod.getUnidadeMedida().getNome().equals(unidade) && prod.getNome().equals(nomeProduto)).iterator().next();
				Bandeira bandeira = bandeiras.stream().filter(band->band.getNome().equals(nomeBandeira)).iterator().next();

				valCompra = valCompra.isEmpty() ? "0,0000":valCompra;
				valVenda  = valVenda.isEmpty() ? "0,000":valVenda;
				BigDecimal valorCompra   = new BigDecimal(0.0000);
				BigDecimal valorVenda    = new BigDecimal(0.000);
				
				valorCompra = valorCompra.add(BigDecimal.valueOf(Double.valueOf(valCompra.replace(",", "."))));
				valorVenda  = valorVenda.add(BigDecimal.valueOf(Double.valueOf(valVenda.replace(",", "."))));

				//tratamento da data
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date dateColeta = new Date(System.currentTimeMillis());
				try {
					dateColeta = dateFormat.parse(dataColeta);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				HistoricoPrecoCombustivel historicoPrecoCombustivel = new HistoricoPrecoCombustivel();
				historicoPrecoCombustivel.setRevenda(revenda);
				historicoPrecoCombustivel.setProduto(produto);
				historicoPrecoCombustivel.setBandeira(bandeira);
				historicoPrecoCombustivel.setDataColeta(dateColeta);
				historicoPrecoCombustivel.setValorCompra(valorCompra);
				historicoPrecoCombustivel.setValorVenda(valorVenda);
				rows.add(historicoPrecoCombustivel);
				
			});
		} catch (Exception e) {
			System.out.println(teste.toString());
			throw new Exception(e.getMessage());
		}
		
		return historicoPrecoCombustivelRepository.saveAll(rows);
		
	}
	
}
