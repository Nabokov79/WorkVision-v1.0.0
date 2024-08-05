package ru.nabokovsg.documentNK.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${laboratoryNK-server.url}")
    private String laboratoryNKUrl;
    @Value(value = "${company-server.url}")
    private String companyUrl;

    @Value(value = "${diagnosedEquipment-server.url}")
    private String diagnosedEquipmentUrl;

    @Value(value = "${diagnosedNK-server.url}")
    private String diagnosedNKUrl;

    @Bean
    public WebClient webLaboratoryNK() {
        return WebClient.builder()
                .baseUrl(laboratoryNKUrl)
                .build();
    }

    @Bean
    public WebClient webCompany() {
        return WebClient.builder()
                .baseUrl(companyUrl)
                .build();
    }

    @Bean
    public WebClient webDiagnosedEquipment() {
        return WebClient.builder()
                .baseUrl(diagnosedEquipmentUrl)
                .build();
    }

    @Bean
    public WebClient webDiagnosedNK() {
        return WebClient.builder()
                .baseUrl(diagnosedNKUrl)
                .build();
    }
}
