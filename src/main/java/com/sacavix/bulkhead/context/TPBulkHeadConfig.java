package com.sacavix.bulkhead.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;

@Configuration
public class TPBulkHeadConfig {
	
	private Logger log = LoggerFactory.getLogger(TPBulkHeadConfig.class);
	
	@Bean
	ThreadPoolBulkhead threadPoolBulkhead() {
		
		ThreadPoolBulkhead th =  this.buildPoolThreadPool().bulkhead("mi-primer-bulkhead");
		
		
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
	
	
	ThreadPoolBulkheadRegistry buildPoolThreadPool() {
        return ThreadPoolBulkheadRegistry.of(ThreadPoolBulkheadConfig.custom()
            .maxThreadPoolSize(10)  //The maximum number threads in the pool ()
            .coreThreadPoolSize(3) //The core number of threads in the pool
            .queueCapacity(3) //The capacity of the queue (in wait)
            .build());
    }

}
