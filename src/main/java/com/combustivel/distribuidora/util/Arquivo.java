package com.combustivel.distribuidora.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.combustivel.distribuidora.enumeration.TipoExtensaoArquivo;

public class Arquivo {

	public static List<String> linhasArquivo(String caminhoArquivo) throws Exception {

		List<String> linhas = new ArrayList<>();
		
		File arquivo = new File(caminhoArquivo);
		
		//validar existencia do arquivo
		if(!arquivo.exists()) {
			throw new Exception("Arquivo não encontrado");
		}
		
		//validar extensao
		String extensao = FilenameUtils.getExtension(caminhoArquivo);
		List<TipoExtensaoArquivo> tiposExtencao = Arrays.asList(TipoExtensaoArquivo.values());
		if(tiposExtencao.stream().filter(te-> te.getExtensao().equals(extensao)).count()==0L) {
			throw new Exception("Extensão -".concat(extensao).concat("- não é permitida!"));
		}
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(arquivo)); 
			while(br.ready()){ 
				linhas.add(br.readLine()); 
			} 
			br.close(); 
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		
		return linhas;
		
	}
	
}
