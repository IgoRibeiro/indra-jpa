package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long>{

}
