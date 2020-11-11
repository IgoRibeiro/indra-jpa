package com.combustivel.distribuidora.controller.rest;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;
import com.combustivel.distribuidora.entity.Municipio;
import com.combustivel.distribuidora.entity.Regiao;
import com.combustivel.distribuidora.entity.Revenda;
import com.combustivel.distribuidora.entity.SiglaRegiao;
import com.combustivel.distribuidora.enumeration.TipoMedia;
import com.combustivel.distribuidora.enumeration.TipoPesquisaMedia;
import com.combustivel.distribuidora.response.BandeiraResponse;
import com.combustivel.distribuidora.response.DataColetaResponse;
import com.combustivel.distribuidora.response.MediaResponse;
import com.combustivel.distribuidora.service.HistoricoPrecoCombustivelService;
import com.combustivel.distribuidora.util.Paths;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping(value = Paths.HISTORICO_PRECO_COMBUSTIVEL, produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoricoPrecoCombustivelResource {
	
	@Autowired
	private HistoricoPrecoCombustivelService historicoPrecoCombustivelService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de Historico de Preco Paginada e/ou por sigla da Regiao"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping
	private Page<HistoricoPrecoCombustivel> listarPaginado(
				@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size,
		        @RequestParam(defaultValue = "") String siglaRegiao
			){
		SiglaRegiao siglaReg = new SiglaRegiao();
		siglaRegiao = siglaRegiao.isEmpty() ? null : siglaRegiao;
		siglaReg.setSigla(siglaRegiao);
		HistoricoPrecoCombustivel historicoPrecoCombustivel = new HistoricoPrecoCombustivel();
		historicoPrecoCombustivel.setRevenda(new Revenda());
		historicoPrecoCombustivel.getRevenda().setMunicipio(new Municipio());
		historicoPrecoCombustivel.getRevenda().getMunicipio().setRegiao(new Regiao());
		historicoPrecoCombustivel.getRevenda().getMunicipio().getRegiao().setSiglaRegiao(siglaReg);
		
		Page<HistoricoPrecoCombustivel> result = historicoPrecoCombustivelService.listarPaginado(historicoPrecoCombustivel, page, size);
		
		return new PageImpl<>(result.getContent(), PageRequest.of(page, size), result.getTotalElements());
		//return new ResponseEntity<List<HistoricoPrecoCombustivel>>(historicoPrecoCombustivelService.listarPaginado(historicoPrecoCombustivel, page, size), HttpStatus.OK);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Historico de Preco por ID"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/{id}")
	private ResponseEntity<HistoricoPrecoCombustivel> procurarPorId(@PathVariable long id) {
		return historicoPrecoCombustivelService.procurarPorId(id).map(h-> ResponseEntity.ok().body(h)).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cadastra um novo Historico"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@PostMapping
	private ResponseEntity<HistoricoPrecoCombustivel> novo(@Validated @RequestBody HistoricoPrecoCombustivel historicoPrecoCombustivel) {
		return ResponseEntity.of(Optional.of(historicoPrecoCombustivelService.salvar(historicoPrecoCombustivel)));
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Altera o Historico"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@PutMapping
	private ResponseEntity<HistoricoPrecoCombustivel> alterar(@Validated @RequestBody HistoricoPrecoCombustivel historicoPrecoCombustivel) {
		return ResponseEntity.of(Optional.of(historicoPrecoCombustivelService.salvar(historicoPrecoCombustivel)));
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Remove o Historico de Preco"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@DeleteMapping("/{id}")
	private void excluir(@PathVariable long id) {
		historicoPrecoCombustivelService.excluir(id);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Media de Preco dos Produtos por Municipio"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/media/preco-municipio")
	private ResponseEntity<MediaResponse> mediaPrecoMunicipio(@RequestParam(required = true) String nomeMunicipio) {
		return ResponseEntity.ok().body(historicoPrecoCombustivelService.media(TipoPesquisaMedia.MUNICIPIO,nomeMunicipio, TipoMedia.PRECO));
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Media do Valor de Compra e Venda do Produto por Municipio"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/media/compra-venda-municipio")
	private ResponseEntity<MediaResponse> mediaCompraVendaMunicipio(@RequestParam(required = true) String nomeMunicipio) {
		return ResponseEntity.ok().body(historicoPrecoCombustivelService.media(TipoPesquisaMedia.MUNICIPIO, nomeMunicipio, TipoMedia.COMPRA_VENDA));
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Media do Valor de Compra e Venda do Produto por Bandeira"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/media/compra-venda-bandeira")
	private ResponseEntity<MediaResponse> mediaCompraVendaBandeira(@RequestParam(required = true) String nomeBandeira) {
		return ResponseEntity.ok().body(historicoPrecoCombustivelService.media(TipoPesquisaMedia.BANDEIRA, nomeBandeira, TipoMedia.COMPRA_VENDA));
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Lista Paginada do Historico Agrupada pela Distribuidora(Bandeira)"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/listar/agrupado-distribuidora")
	private ResponseEntity<Page<BandeiraResponse>> listarDistribuidoraPaginado(
				@Validated @RequestBody Bandeira bandeira,
				@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size
			){
		return new ResponseEntity<Page<BandeiraResponse>>(historicoPrecoCombustivelService.listarDistribuidoraPaginado(bandeira, page, size), HttpStatus.OK);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Lista Paginada do Historico Agrupada pela Data da Coleta"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/listar/agrupado-data-coleta")
	private ResponseEntity<Page<DataColetaResponse>> listarDataColetaPaginado(
				@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataColeta,
				@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size
			){
		return new ResponseEntity<Page<DataColetaResponse>>(historicoPrecoCombustivelService.listarDataColetaPaginado(dataColeta, page, size), HttpStatus.OK);
	}
	
}
