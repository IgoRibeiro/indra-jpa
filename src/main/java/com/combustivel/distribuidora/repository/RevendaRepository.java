package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.Revenda;

@Repository
public interface RevendaRepository extends JpaRepository<Revenda, Long>{

}
