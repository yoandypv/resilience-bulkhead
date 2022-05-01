package com.sacavix.bulkhead;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sacavix.bulkhead.service.SampleService;

@RestController
@RequestMapping("/products")
public class SampleController {

	private final SampleService sampleService;

	public SampleController(SampleService sampleService) {
		super();
		this.sampleService = sampleService;
	}
	
	@GetMapping
	public String get() {
		return this.sampleService.callMockedProductApi();
	}
	
}
