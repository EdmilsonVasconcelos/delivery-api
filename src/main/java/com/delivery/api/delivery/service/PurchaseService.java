package com.delivery.api.delivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.converter.purchase.Converter;
import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.product.response.ProductReponseDTO;
import com.delivery.api.delivery.dto.purchase.request.PurchaseRequestDTO;
import com.delivery.api.delivery.dto.purchase.response.PurchaseResponseDTO;
import com.delivery.api.delivery.exception.ProductNotExistsException;
import com.delivery.api.delivery.exception.PurchaseNotExistsException;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Purchase;
import com.delivery.api.delivery.repository.ProductRepository;
import com.delivery.api.delivery.repository.PurchaseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PurchaseService {
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	private static final String PURCHASE_WITH_ID_NOT_EXISTS = "pedido com id %s nao existe";
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public PurchaseResponseDTO savePurchase(PurchaseRequestDTO request) {
		
		log.debug("PurchaseService.savePurchase - Start - Request:  [{}]", request);
		
		Customer customer = mapper.map(request.getCustomer(), Customer.class);
		
		List<Product> products = getProductsPurchase(request.getProducts());
		
		Purchase purchaseToSave = Converter.toPurachase(request, customer, products);
		
		Purchase purchaseSaved = purchaseRepository.save(purchaseToSave);
		
		CustomerResponseDTO customerSaved = mapper.map(purchaseSaved.getCustomer(), CustomerResponseDTO.class);

		List<ProductReponseDTO> productsSaved = getProductsPurchaseResponseDTO(purchaseSaved.getProducts());

		PurchaseResponseDTO response = Converter.toPurchaseResponseDTO(purchaseSaved, customerSaved, productsSaved);

		log.debug("PurchaseService.savePurchase - Finish - Request [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public List<PurchaseResponseDTO> getAllpurchases() {
		
		log.debug("PurchaseService.getAllpurchases - Start");
		
		List<Purchase> allRequests = purchaseRepository.findAll();

		List<PurchaseResponseDTO> response = allRequests.stream().map(request -> mapper.map(request, PurchaseResponseDTO.class)).collect(Collectors.toList());
		
		log.debug("PurchaseService.getAllpurchases - Finish -  Response:  [{}]", response);
		
		
		return response;
		
	}
	
	public PurchaseResponseDTO alterStatusPurchase(Long idPurchase) {
		
		log.debug("PurchaseService.closePurchase - Start - alterStatusPurchase:  [{}]", idPurchase);
		
		Purchase purchase = getPurchaseById(idPurchase);
		
		Purchase purchaseSaved = purchaseRepository.save(purchase);
		
		PurchaseResponseDTO response = mapper.map(purchaseSaved, PurchaseResponseDTO.class);
		
		log.debug("PurchaseService.closePurchase - Finish - alterStatusPurchase [{}], Response:  [{}]", idPurchase, response);
		
		return response;
		
	}
	
	private Purchase getPurchaseById(Long idPurchase) {
		
		log.debug("PurchaseService.getPurchaseById - Start - idPurchase:  [{}]", idPurchase);
		
		Purchase purchase = purchaseRepository.findById(idPurchase)
				.orElseThrow(() -> new PurchaseNotExistsException(String.format(PURCHASE_WITH_ID_NOT_EXISTS, idPurchase)));
		
		log.debug("PurchaseService.getPurchaseById - Finish - idPurchase [{}], Response:  [{}]", idPurchase, purchase);
		
		return purchase;
		
	}
	
	private List<ProductReponseDTO> getProductsPurchaseResponseDTO(List<Product> products) {
		
		log.debug("PurchaseService.getProductsPurchaseResponseDTOByListProducts - Start - Request:  [{}]", products);
		
		List<ProductReponseDTO> response = products.stream().map(product -> mapper.map(product, ProductReponseDTO.class)).collect(Collectors.toList());
		
		log.debug("PurchaseService.getProductsPurchaseResponseDTOByListProducts - Finish - Request:  [{}], Response: [{}]", products, response);
		
		return response;
	
	}
	
	private List<Product> getProductsPurchase(List<Long> products) {
		
		log.debug("PurchaseService.getProductsPurchase - Start - ids:  [{}]", products);
		
		List<Product> response = new ArrayList<>();
		
		products.forEach(productRequestDTO -> {
			
			Product product = getProductById(productRequestDTO);
			
			response.add(product);
			
		});
		
		log.debug("PurchaseService.getProductsPurchase - Finish - ids:  [{}], Response: [{}]", products, response);
		
		return response;
		
	}
	
	private Product getProductById(Long id) {
		
		log.debug("PurchaseService.getProductById - Start - id:  [{}]", id);
		
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isPresent()) {
			
			return product.get();
			
		}
		
		log.debug("PurchaseService.getProductById - Finish - id:  [{}]", id);
		
		throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		
	}

}
