package com.udyata.linentrack.linentrack.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveJob {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://linen-track.onrender.com/api/asset-category";

    @Scheduled(fixedDelay = 600000)
    public void performKeepAliveCall() {
        try {
            restTemplate.getForObject(url, String.class);
            System.out.println("Keep-alive request sent.");
        } catch (Exception e) {
            System.err.println("Failed to send keep-alive request: " + e.getMessage());
        }
    }
}
