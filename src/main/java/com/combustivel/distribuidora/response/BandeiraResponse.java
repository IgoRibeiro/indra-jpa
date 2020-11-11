package com.combustivel.distribuidora.response;

import java.util.List;

import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;
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
public class BandeiraResponse {

	private Long id;
	
	private String nome;
	
	private List<HistoricoPrecoCombustivel> historicoPrecosCombustivel;
	
}
