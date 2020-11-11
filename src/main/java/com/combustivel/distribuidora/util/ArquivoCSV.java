package com.combustivel.distribuidora.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArquivoCSV {
	

	public static List<List<String>> getRegistros(String caminhoArquivo, String separator, Boolean firstLineIsHeader) throws Exception{
		List<String> linhas = Arquivo.linhasArquivo(caminhoArquivo);
		List<List<String>> registros = new ArrayList<>();
		
		if(firstLineIsHeader) {linhas.remove(0);}
		
		linhas.stream().forEach(l->{
			List<String> colunas = Arrays.asList(l.split(separator));
			registros.add(colunas);
		});
		
		return registros;
	}
	
	
}
