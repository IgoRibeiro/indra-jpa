package com.combustivel.distribuidora.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ProdutoResponse {

	private Long id;
	
	private String nome;
	
	private BigDecimal mediaPreco;
	
	private BigDecimal mediaCompra;

	private BigDecimal mediaVenda;
	
}
