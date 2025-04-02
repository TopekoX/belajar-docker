package com.timposulabs.belajar.docker.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

	@GetMapping("/hello")
	public Map<String, String> sayHello () throws UnknownHostException {
		InetAddress ipAddress = InetAddress.getLocalHost();
		return Map.of("message", "Halo Bro",
				"ipAddress", ipAddress.getHostAddress(),
				"hostname", ipAddress.getHostName());
	}
}
