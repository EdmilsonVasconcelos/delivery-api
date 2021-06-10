package com.delivery.api.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.api.delivery.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
