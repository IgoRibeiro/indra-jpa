package com.combustivel.distribuidora.response;

import java.math.BigDecimal;
import java.util.List;

import com.combustivel.distribuidora.entity.Regiao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class MediaResponse {
	
	private Long id;
	
	private String nome;

	private Regiao regiao;
	
	private List<ProdutoResponse> produtos;
	
	private BigDecimal mediaCompra;

	private BigDecimal mediaVenda;
	
	
}
