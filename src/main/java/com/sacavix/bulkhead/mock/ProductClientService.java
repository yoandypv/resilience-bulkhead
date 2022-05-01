package com.sacavix.bulkhead.mock;

import org.springframework.stereotype.Service;

@Service
public class ProductClientService {
	
	public String getProductNameMocked(int id) {
		if(id%2==0) {
			try {
			Thread.sleep(20000);
			return "Producto lento";
			} catch (Exception e) {
				e.printStackTrace();	
			}
		}
		return "Producto Rapido";
	}

}
