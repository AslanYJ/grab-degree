package com.sharding.demo.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * WorkerIdConfig
 * @author yjlan
 */
@Configuration
public class WorkerIdConfig {
	private static final Logger logger = LoggerFactory.getLogger(WorkerIdConfig.class);
	
	static {
		String workerId = "0";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			workerId = String.valueOf(Math.abs(ip.hashCode() % 1024));
		} catch (UnknownHostException e) {
			logger.error("workerId system property set fail,e:[{}]",e.getMessage());
		}
		System.setProperty("workerId", workerId);
		logger.info("workerId system property set success , workerId:[{}]",workerId);

	}
}