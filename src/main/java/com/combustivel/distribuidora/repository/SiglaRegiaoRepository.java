package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.SiglaRegiao;

@Repository
public interface SiglaRegiaoRepository extends JpaRepository<SiglaRegiao, Long>{

}
