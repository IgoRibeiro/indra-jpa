package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.HistoricoPrecoCombustivel;

@Repository
public interface HistoricoPrecoCombustivelRepository extends JpaRepository<HistoricoPrecoCombustivel, Long>{

}
