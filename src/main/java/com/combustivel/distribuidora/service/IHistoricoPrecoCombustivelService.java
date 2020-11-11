package com.combustivel.distribuidora.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;
import com.combustivel.distribuidora.enumeration.TipoMedia;
import com.combustivel.distribuidora.enumeration.TipoPesquisaMedia;
import com.combustivel.distribuidora.response.BandeiraResponse;
import com.combustivel.distribuidora.response.DataColetaResponse;
import com.combustivel.distribuidora.response.MediaResponse;

public interface IHistoricoPrecoCombustivelService {
	
	public List<HistoricoPrecoCombustivel> listar();
	
    public Page<HistoricoPrecoCombustivel> listarPaginado(
    		HistoricoPrecoCombustivel historicoPrecoCombustivel,
            int page,
            int size) ;
    
    public Optional<HistoricoPrecoCombustivel> procurarPorId(Long id);

    public HistoricoPrecoCombustivel salvar(HistoricoPrecoCombustivel entity);
    
    public void excluir(Long id);
    
    public MediaResponse media(TipoPesquisaMedia tipoPesquisaMedia, String nome, TipoMedia tipoMedia);

    public Page<BandeiraResponse> listarDistribuidoraPaginado(
    		Bandeira bandeira,
            int page,
            int size);
    
    public Page<DataColetaResponse> listarDataColetaPaginado(
    		Date dataColeta,
            int page,
            int size);
    
    
}
