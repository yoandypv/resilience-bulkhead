package com.sacavix.bulkhead.service;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacavix.bulkhead.mock.ProductClientService;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.decorators.Decorators;

@Service
public class SampleService {
	
	private Logger log = LoggerFactory.getLogger(SampleService.class);
	
	// pls, usa inyeccion por constructor
	
	@Autowired
	private ProductClientService productClientService;
	
	@Autowired
	private ThreadPoolBulkhead threadPoolBulkhead;
	
	@Autowired
	private Bulkhead semaforeBulkhead;
	
	
	public String callMockedProductApi() {
		
			return Decorators
					.ofSupplier(() -> this.productClientService.getProductNameMocked(2)) // pass a Supplier 
					//.withThreadPoolBulkhead(threadPoolBulkhead)  // set bulkhead instance to use
					.withBulkhead(semaforeBulkhead)  // set bulkhead instance to use
					.withFallback(Arrays.asList(BulkheadFullException.class), this::getProductNameMockedFallback) // fallback for exceptions
					.get();
					//.toCompletableFuture()
				//	.get(); // wait for response and get result or fallback

	}
	
	public String getProductNameMockedFallback(Throwable err) {
		log.error("Fallback is called, with error {}", err.getMessage());
		return "Producto Fallback";
	}
	
	

}
