package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
