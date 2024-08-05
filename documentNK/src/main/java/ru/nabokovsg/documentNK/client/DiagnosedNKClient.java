package ru.nabokovsg.documentNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.VisualMeasuringSurveyDto;

import java.util.List;

@Component
public class DiagnosedNKClient {

    private final WebClient client;

    @Autowired
    public DiagnosedNKClient(@Qualifier(value = "webCompany") WebClient client) {
        this.client = client;
    }

    public EquipmentGeodesicMeasurementsDto getEquipmentGeodesicMeasurements(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(EquipmentGeodesicMeasurementsDto.class)
                .block();
    }

    public List<VisualMeasuringSurveyDto> getVisualMeasuringSurvey(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(VisualMeasuringSurveyDto.class)
                .buffer()
                .blockFirst();
    }
}