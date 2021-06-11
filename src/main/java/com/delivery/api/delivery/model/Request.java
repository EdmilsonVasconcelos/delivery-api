package com.delivery.api.delivery.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.delivery.api.delivery.enums.MethodPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Request {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Embedded
	private Customer customer;
	
	@ManyToMany
	private List<Product> products = new ArrayList<>();
	
	private BigDecimal value;
	
	private String observation;
	
	private Boolean isOpen;
	
	@Enumerated(EnumType.STRING)
	private MethodPayment methodPayment;
	
    @CreatedDate
    @Column(updatable = false)
	private LocalDateTime created;
		
    @LastModifiedDate
	private LocalDateTime updated;

}
