package com.combustivel.distribuidora.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Revenda {

	@Id
	@Column(name = "id_revenda")
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_municipio", nullable = false)
	private Municipio municipio;
	
}
