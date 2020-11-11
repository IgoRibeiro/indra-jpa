package com.combustivel.distribuidora.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;
import com.combustivel.distribuidora.entity.Municipio;
import com.combustivel.distribuidora.entity.Produto;
import com.combustivel.distribuidora.entity.Revenda;
import com.combustivel.distribuidora.enumeration.TipoMedia;
import com.combustivel.distribuidora.enumeration.TipoPesquisaMedia;
import com.combustivel.distribuidora.repository.HistoricoPrecoCombustivelRepository;
import com.combustivel.distribuidora.response.BandeiraResponse;
import com.combustivel.distribuidora.response.DataColetaResponse;
import com.combustivel.distribuidora.response.MediaResponse;
import com.combustivel.distribuidora.response.ProdutoResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HistoricoPrecoCombustivelService implements IHistoricoPrecoCombustivelService{
	
	@Autowired
	private HistoricoPrecoCombustivelRepository historicoPrecoCombustivelRepository;
	
	public List<HistoricoPrecoCombustivel> listar(){
		return Optional.ofNullable(historicoPrecoCombustivelRepository.findAll()).orElse(new ArrayList<HistoricoPrecoCombustivel>());
	}
	
    public Page<HistoricoPrecoCombustivel> listarPaginado(
    		HistoricoPrecoCombustivel historicoPrecoCombustivel,
            int page,
            int size) {
    	
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "dataColeta");

    	Example<HistoricoPrecoCombustivel> example = Example.of(historicoPrecoCombustivel, ExampleMatcher.matchingAll().withIgnoreCase());

    	return historicoPrecoCombustivelRepository.findAll(example, pageRequest);
    }
    
    public Optional<HistoricoPrecoCombustivel> procurarPorId(Long id) {
    	return historicoPrecoCombustivelRepository.findById(id);
    }

    public HistoricoPrecoCombustivel salvar(HistoricoPrecoCombustivel entity) {
    	return historicoPrecoCombustivelRepository.save(entity);
    }
    
    public void excluir(Long id) {
    	historicoPrecoCombustivelRepository.delete(procurarPorId(id).get());
    }
    
    public MediaResponse media(TipoPesquisaMedia tipoPesquisaMedia, String nome, TipoMedia tipoMedia){
    	
    	HistoricoPrecoCombustivel historicoPrecoCombustivel = new HistoricoPrecoCombustivel();
    	historicoPrecoCombustivel.setBandeira(new Bandeira());
    	historicoPrecoCombustivel.setRevenda(new Revenda());
    	historicoPrecoCombustivel.getRevenda().setMunicipio(new Municipio());

    	switch (tipoPesquisaMedia) {
		case MUNICIPIO:
	    	historicoPrecoCombustivel.getRevenda().getMunicipio().setNome(nome);
			break;
		default:
	    	historicoPrecoCombustivel.getBandeira().setNome(nome);
			break;
		}
    	
    	Example<HistoricoPrecoCombustivel> example = Example.of(historicoPrecoCombustivel, ExampleMatcher.matchingAll().withIgnoreCase());
    	
    	List<HistoricoPrecoCombustivel> historicos = historicoPrecoCombustivelRepository.findAll(example);

    	Map<Produto, List<HistoricoPrecoCombustivel>> mapProdutos = historicos.stream().collect(Collectors.groupingBy(h-> h.getProduto()));//;Each(m-> municipios.add(m.));
    	
    	if(historicos==null || historicos.size()==0) {
    		return new MediaResponse();
    	}
    	
    	Municipio municipio = historicos.iterator().next().getRevenda().getMunicipio(); 
    	Bandeira  bandeira  = historicos.iterator().next().getBandeira(); 

    	Long idMediaResponse = tipoPesquisaMedia.equals(TipoPesquisaMedia.MUNICIPIO) ? municipio.getId() : bandeira.getId(); 
    	
    	MediaResponse mediaResponse = new MediaResponse();
    	mediaResponse.setId(idMediaResponse);
    	mediaResponse.setNome(nome);
    	mediaResponse.setProdutos(new ArrayList<ProdutoResponse>());
    	
    	mapProdutos.entrySet().stream().forEach(p-> {
    		BigDecimal valorTotalVenda = new BigDecimal(p.getValue().stream().mapToDouble(h-> h.getValorVenda().doubleValue()).sum());
    		valorTotalVenda.setScale(4, RoundingMode.HALF_UP);
    		
    		BigDecimal valorTotalCompra = new BigDecimal(p.getValue().stream().mapToDouble(h-> h.getValorCompra().doubleValue()).sum());
    		valorTotalCompra.setScale(4, RoundingMode.HALF_UP);

    		BigDecimal qtdRegistros = new BigDecimal(p.getValue().size());
    		qtdRegistros.setScale(4);
    		
    		//preco e venda sao os mesmo
    		BigDecimal mediaPreco = new BigDecimal(0.0000);
    		mediaPreco = mediaPreco.add(valorTotalVenda).divide(qtdRegistros, 4, RoundingMode.HALF_UP);
    		
    		BigDecimal mediaCompra = new BigDecimal(0.0000);
    		mediaCompra = mediaCompra.add(valorTotalCompra).divide(qtdRegistros, 4, RoundingMode.HALF_UP);
    		

    		ProdutoResponse produtoResponse = new ProdutoResponse();
    		produtoResponse.setId(p.getKey().getId());
    		produtoResponse.setNome(p.getKey().getNome());
    		
    		switch (tipoMedia) {
			case PRECO:
	    		produtoResponse.setMediaPreco(mediaPreco);
	    		break;

			case COMPRA:
    			produtoResponse.setMediaCompra(mediaCompra);
    			break;

			case VENDA:
    			produtoResponse.setMediaVenda(mediaPreco);
    			break;

			case COMPRA_VENDA:
    			produtoResponse.setMediaCompra(mediaCompra);
    			produtoResponse.setMediaVenda(mediaPreco);
    			break;

			default:
    			produtoResponse.setMediaPreco(mediaPreco);
    			produtoResponse.setMediaVenda(mediaPreco);
    			produtoResponse.setMediaCompra(mediaCompra);
				break;
			}
    		
    		mediaResponse.getProdutos().add(produtoResponse);
    	});
    	
    	return Optional.ofNullable(mediaResponse).orElse(new MediaResponse());
    }

    
    public Page<BandeiraResponse> listarDistribuidoraPaginado(
    		Bandeira bandeira,
            int page,
            int size) {
    	
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "bandeira.nome");
        
        HistoricoPrecoCombustivel historicoPrecoCombustivel = new HistoricoPrecoCombustivel();
        historicoPrecoCombustivel.setBandeira(bandeira);

    	Example<HistoricoPrecoCombustivel> example = Example.of(historicoPrecoCombustivel, ExampleMatcher.matchingAll().withIgnoreCase());

    	Page<HistoricoPrecoCombustivel> historicos = historicoPrecoCombustivelRepository.findAll(example, pageRequest);
    	
    	Map<Bandeira, List<HistoricoPrecoCombustivel>> mapBandeiras = historicos.stream().collect(Collectors.groupingBy(h-> h.getBandeira()));
    	
    	List<BandeiraResponse> bandeirasResponse = new ArrayList<>();
    	mapBandeiras.entrySet().stream().forEach(b->{
    		BandeiraResponse bandeiraResponse = new BandeiraResponse();
    		bandeiraResponse.setId(b.getKey().getId());
    		bandeiraResponse.setNome(b.getKey().getNome());
    		bandeiraResponse.setHistoricoPrecosCombustivel(b.getValue());
    		
    		bandeirasResponse.add(bandeiraResponse);
    	});
    	
    	return new PageImpl<BandeiraResponse>(bandeirasResponse, pageRequest, bandeirasResponse.size());
    }
    
    public Page<DataColetaResponse> listarDataColetaPaginado(
    		Date dataColeta,
            int page,
            int size) {
    	
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "dataColeta");
        
        HistoricoPrecoCombustivel historicoPrecoCombustivel = new HistoricoPrecoCombustivel();
        historicoPrecoCombustivel.setDataColeta(dataColeta);

    	Example<HistoricoPrecoCombustivel> example = Example.of(historicoPrecoCombustivel, ExampleMatcher.matchingAll().withIgnoreCase());

    	Page<HistoricoPrecoCombustivel> historicos = historicoPrecoCombustivelRepository.findAll(example, pageRequest);
    	
    	Map<Date, List<HistoricoPrecoCombustivel>> mapBandeiras = historicos.stream().collect(Collectors.groupingBy(h-> h.getDataColeta()));
    	
    	List<DataColetaResponse> datasResponse = new ArrayList<>();
    	mapBandeiras.entrySet().stream().forEach(b->{
    		DataColetaResponse dataColetaResponse = new DataColetaResponse();
    		dataColetaResponse.setDataColeta(b.getKey());
    		dataColetaResponse.setHistoricoPrecosCombustivel(b.getValue());
    		
    		datasResponse.add(dataColetaResponse);
    	});
    	
    	return new PageImpl<DataColetaResponse>(datasResponse, pageRequest, datasResponse.size());
    }
    
    
}
