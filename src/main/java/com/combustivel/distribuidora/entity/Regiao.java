package com.combustivel.distribuidora.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = {"id"})
@Entity
@Data
public class Regiao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_regiao")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_sigla_regiao", nullable = false)
	private SiglaRegiao siglaRegiao;
	
	@ManyToOne
	@JoinColumn(name = "id_estado", nullable = false)
	private Estado estado;
	
}
