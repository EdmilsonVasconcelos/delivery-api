package com.delivery.api.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.api.delivery.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	List<Purchase> findByIsOpenTrue();

}
