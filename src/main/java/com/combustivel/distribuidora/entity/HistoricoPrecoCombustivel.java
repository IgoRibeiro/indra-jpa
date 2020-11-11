package com.combustivel.distribuidora.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "historico_preco_combustivel")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = {"id"})
@Entity
@Data
public class HistoricoPrecoCombustivel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_coleta")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_revenda", nullable = false)
	private Revenda revenda;
	
	@ManyToOne
	@JoinColumn(name = "id_bandeira", nullable = false)
	private Bandeira bandeira;
	
	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_coleta", nullable = false)
	private Date dataColeta;
	
	@Column(name = "valor_compra", nullable = false, precision = 20, scale = 4)
	private BigDecimal valorCompra;
	
	@Column(name = "valor_venda", nullable = false, precision = 20, scale = 4)
	private BigDecimal valorVenda;
	
}
