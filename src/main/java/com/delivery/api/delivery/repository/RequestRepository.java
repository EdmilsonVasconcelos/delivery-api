package com.delivery.api.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.api.delivery.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
	
	List<Request> findByIsOpenTrue();

}
