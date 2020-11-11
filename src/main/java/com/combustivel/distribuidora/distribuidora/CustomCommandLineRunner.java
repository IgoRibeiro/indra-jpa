package com.combustivel.distribuidora.distribuidora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.combustivel.distribuidora.controller.csv.BandeiraRow;
import com.combustivel.distribuidora.controller.csv.EstadoRow;
import com.combustivel.distribuidora.controller.csv.HistoricoPrecoCombustivelRow;
import com.combustivel.distribuidora.controller.csv.MunicipioRow;
import com.combustivel.distribuidora.controller.csv.ProdutoRow;
import com.combustivel.distribuidora.controller.csv.RegiaoRow;
import com.combustivel.distribuidora.controller.csv.RevendaRow;
import com.combustivel.distribuidora.controller.csv.SiglaRegiaoRow;
import com.combustivel.distribuidora.controller.csv.UnidadeMedidaRow;
import com.combustivel.distribuidora.entity.Bandeira;
import com.combustivel.distribuidora.entity.Estado;
import com.combustivel.distribuidora.entity.Municipio;
import com.combustivel.distribuidora.entity.Produto;
import com.combustivel.distribuidora.entity.Regiao;
import com.combustivel.distribuidora.entity.Revenda;
import com.combustivel.distribuidora.entity.SiglaRegiao;
import com.combustivel.distribuidora.entity.UnidadeMedida;
import com.combustivel.distribuidora.repository.BandeiraRepository;
import com.combustivel.distribuidora.repository.EstadoRepository;
import com.combustivel.distribuidora.repository.HistoricoPrecoCombustivelRepository;
import com.combustivel.distribuidora.repository.MunicipioRepository;
import com.combustivel.distribuidora.repository.ProdutoRepository;
import com.combustivel.distribuidora.repository.RegiaoRepository;
import com.combustivel.distribuidora.repository.RevendaRepository;
import com.combustivel.distribuidora.repository.SiglaRegiaoRepository;
import com.combustivel.distribuidora.repository.UnidadeMedidaRepository;
import com.combustivel.distribuidora.util.ArquivoCSV;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
class CustomCommandLinerRunner implements CommandLineRunner{

	@Autowired
	private BandeiraRepository bandeiraRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private HistoricoPrecoCombustivelRepository historicoPrecoCombustivelRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Autowired
	private RevendaRepository revendaRepository;
	
	@Autowired
	private SiglaRegiaoRepository siglaRegiaoRepository;
	
	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		try {
			log.info("Iniciando Importação do Arquivo 2018-1_CA.csv...");
			
			String caminhoArquivo = ResourceUtils.getFile("classpath:2018-1_CA.csv").getAbsolutePath();
			List<List<String>> registros = ArquivoCSV.getRegistros(caminhoArquivo, "  ", true);
			
			log.info("Importando Bandeira...");
			BandeiraRow bandeiraRow = new BandeiraRow();
			List<Bandeira> bandeiras = bandeiraRow.salvarRegistros(registros, bandeiraRepository);
			
			log.info("Importando Estado...");
			EstadoRow estadoRow = new EstadoRow();
			List<Estado> estados = estadoRow.salvarRegistros(registros, estadoRepository);
			
			log.info("Importando Unidade de Medida...");
			UnidadeMedidaRow unidadeMedidaRow = new UnidadeMedidaRow();
			List<UnidadeMedida> unidadesMedida = unidadeMedidaRow.salvarRegistros(registros, unidadeMedidaRepository);
			
			log.info("Importando Sigla da Região...");
			SiglaRegiaoRow siglaRegiaoRow = new SiglaRegiaoRow();
			List<SiglaRegiao> siglasRegiao = siglaRegiaoRow.salvarRegistros(registros, siglaRegiaoRepository);
			
			log.info("Importando Região...");
			RegiaoRow regiaoRow = new RegiaoRow();
			List<Regiao> regioes = regiaoRow.salvarRegistros(registros, regiaoRepository, estados, siglasRegiao);
			
			log.info("Importando Município...");
			MunicipioRow municipioRow = new MunicipioRow();
			List<Municipio> municipios = municipioRow.salvarRegistros(registros, municipioRepository, regioes);
			
			log.info("Importando Produto...");
			ProdutoRow produtoRow = new ProdutoRow();
			List<Produto> produtos = produtoRow.salvarRegistros(registros, produtoRepository, unidadesMedida);
			
			log.info("Importando Revenda...");
			RevendaRow revendaRow = new RevendaRow();
			List<Revenda> revendas = revendaRow.salvarRegistros(registros, revendaRepository, municipios);
			
			log.info("Importando Histório de Preços dos Combustíveis...");
			HistoricoPrecoCombustivelRow historicoPrecoCombustivelRow = new HistoricoPrecoCombustivelRow();
			historicoPrecoCombustivelRow.salvarRegistros(registros, historicoPrecoCombustivelRepository, revendas, produtos, bandeiras);
			
			log.info("Importação Finalizada Com Sucesso!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Arquivo -2018-1_CA.csv- não encontrado!");
		}
		
		
	}
	
}
