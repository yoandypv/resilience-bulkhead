package com.sacavix.bulkhead.context;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

@Configuration
public class SemaforeBulkHeadConfig {
	
private Logger log = LoggerFactory.getLogger(SemaforeBulkHeadConfig.class);
	
	@Bean
	public Bulkhead semaforeBulkhead() {
		
		Bulkhead th =  this.buildPoolSemafore().bulkhead("mi-primer-bulkhead");
		
		
		th.getEventPublisher()
		.onCallPermitted(e -> {
			log.info("Event " + e.getEventType());
		})
		.onCallFinished(e -> {
			log.info("Event " + e.getEventType());
		})
		.onCallRejected(e -> {
			log.info("Event " + e.getEventType());
		});
		
		return th;
	}
	
	
	BulkheadRegistry buildPoolSemafore() {
        return BulkheadRegistry.of(io.github.resilience4j.bulkhead.BulkheadConfig.custom()
        		.maxConcurrentCalls(10)
        	    .maxWaitDuration(Duration.ofMillis(500))
        	    .build());
    }


}
