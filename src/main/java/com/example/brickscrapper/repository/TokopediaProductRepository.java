package com.example.brickscrapper.repository;

import com.example.brickscrapper.model.TokopediaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokopediaProductRepository extends JpaRepository<TokopediaProduct, Long> {

}
