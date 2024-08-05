package ru.nabokovsg.laboratoryNK.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${company-server.url}")
    private String companyUrl;

    @Value(value = "${diagnosticObjectNKUrl-server.url}")
    private String diagnosticObjectNKUrl;

    @Value(value = "${document-server.url}")
    private String documentUrl;

    @Value(value = "${diagnosedNK-server.url}")
    private String diagnosedNKUrl;

    @Bean
    public WebClient webCompany() {
        return WebClient.builder()
                .baseUrl(companyUrl)
                .build();
    }

    @Bean
    public WebClient webDiagnosticObjectNK() {
        return WebClient.builder()
                .baseUrl(diagnosticObjectNKUrl)
                .build();
    }

    @Bean
    public WebClient webDocument() {
        return WebClient.builder()
                .baseUrl(documentUrl)
                .build();
    }

    @Bean
    public WebClient webDiagnosedNK() {
        return WebClient.builder()
                .baseUrl(diagnosedNKUrl)
                .build();
    }
}
