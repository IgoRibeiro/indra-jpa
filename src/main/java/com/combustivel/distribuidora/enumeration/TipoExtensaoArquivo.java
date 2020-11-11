package com.combustivel.distribuidora.enumeration;

import lombok.Getter;

public enum TipoExtensaoArquivo {

	CSV("csv", "Character-Separated Values"),
	TXT("txt", "Arquivo Texto");
	
	@Getter
	private String extensao;

	@Getter
	private String descricao;
	
	TipoExtensaoArquivo(String extensao, String descricao) {
		this.extensao  = extensao;
		this.descricao = descricao;
	}
	
}
