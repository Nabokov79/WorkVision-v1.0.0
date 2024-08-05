package ru.nabokovsg.documentNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentInspectionDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentRepairDto;

import java.util.List;

@Component
public class DiagnosedEquipmentClient {

    private final WebClient client;

    @Autowired
    public DiagnosedEquipmentClient(@Qualifier(value = "webDiagnosedEquipment") WebClient client) {
        this.client = client;
    }

    public List<EquipmentInspectionDto> getEquipmentInspections(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(EquipmentInspectionDto.class)
                .buffer()
                .blockFirst();
    }

    public List<EquipmentRepairDto> getEquipmentRepairs(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(EquipmentRepairDto.class)
                .buffer()
                .blockFirst();
    }
}