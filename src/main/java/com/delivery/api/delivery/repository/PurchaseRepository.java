package com.delivery.api.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.api.delivery.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	

}
