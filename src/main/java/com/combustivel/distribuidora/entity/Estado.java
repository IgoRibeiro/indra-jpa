package com.combustivel.distribuidora.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_estado")
	private Long id;
	
	@Column(nullable = false, unique = true, length = 2)
	private String sigla;
	
}
