package com.dheeraj.weather.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dheeraj.weather.service.WeatherService;

@RestController
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	private Map<String, String> validClientCredentials = new HashMap<>();

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/weather/summary")
	public String getWeatherSummary(@RequestParam String locationName, @RequestHeader("X-Client-Id") String clientId,
			@RequestHeader("X-Client-Secret") String clientSecret) throws IOException {
		System.out.println("hello");
		authenticate(clientId, clientSecret);
		return weatherService.getForecastSummaryByLocationName(locationName);
	}

	@GetMapping("/weather/hourly")
	public String getHourlyWeatherForecast(@RequestParam String locationName,
			@RequestHeader("X-Client-Id") String clientId, @RequestHeader("X-Client-Secret") String clientSecret)
			throws IOException {
		authenticate(clientId, clientSecret);
		return weatherService.getHourlyForecastByLocationName(locationName);
	}

	private void authenticate(String clientId, String clientSecret) {
		String validSecret = validClientCredentials.get(clientId);

		if (validSecret == null || !validSecret.equals(clientSecret)) {
			logger.info("Authentication failed");
			throw new RuntimeException("Authentication failed");
		}else
			logger.info("Valid User: " + clientId);
	}

	@GetMapping("/admin/register-client/{client-id}")
	public String registerClient(@PathVariable("client-id") String clientId) {
		// Generate a random client secret
		String clientSecret = generateRandomClientSecret();

		// Store the valid client secret in the map
		validClientCredentials.put(clientId, clientSecret);
		logger.info("Client registered successfully. Client ID: " + clientId + ", Client Secret: " + clientSecret);
		return "Client registered successfully. Client ID: " + clientId + ", Client Secret: " + clientSecret;
	}

	private String generateRandomClientSecret() {
		return RandomStringUtils.randomAlphanumeric(40);
	}
}
