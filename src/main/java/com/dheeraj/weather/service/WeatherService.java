package com.dheeraj.weather.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    private final String baseUrl = "https://forecast9.p.rapidapi.com/rapidapi/forecast/";

    @Value("${rapidapi.rapid-api-key}")
    private String rapidApiKey;


    public String getForecastSummaryByLocationName(String locationName) throws IOException {
        String apiUrl = baseUrl + locationName + "/summary/";
        return performApiRequest(apiUrl);
    }

    public String getHourlyForecastByLocationName(String locationName) throws IOException {
        String apiUrl = baseUrl + locationName + "/hourly/";
        return performApiRequest(apiUrl);
    }

    private String performApiRequest(String apiUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.setHeader("X-RapidAPI-Key", rapidApiKey);
        httpGet.setHeader("X-RapidAPI-Host", "forecast9.p.rapidapi.com");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            return httpClient.execute(httpGet, response -> {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    throw new IOException("Failed to fetch data from API: " + response.getStatusLine());
                }
            });
        }
    }
}
