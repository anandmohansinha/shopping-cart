package com.java.client;

import com.java.config.ConfigLoader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceApiClient implements PriceApi {

    private final HttpClient client;
    private final String baseUrl;

    public PriceApiClient(HttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;

    }
    public PriceApiClient() {
        this(HttpClient.newHttpClient(), ConfigLoader.loadBaseUrl());
    }

    public double getPrice(String productName) throws Exception {
        String url = baseUrl + productName + ".json";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET().build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        return extractPrice(response.body());
    }

    private double extractPrice(String json){
        Pattern pattern = Pattern.compile("\"price\"\\s*:\\s*(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(json);
        if(matcher.find()){
            return Double.parseDouble(matcher.group(1));
        }
        throw new RuntimeException("Price not found");
    }



}
