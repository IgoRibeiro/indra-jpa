package com.combustivel.distribuidora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.combustivel.distribuidora.entity.Bandeira;

@Repository
public interface BandeiraRepository extends JpaRepository<Bandeira, Long>{

}
