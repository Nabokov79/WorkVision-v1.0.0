package ru.nabokovsg.gateway.client.diagnosedEquipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentInspection.NewEquipmentInspectionDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentInspection.UpdateEquipmentInspectionDto;

@Service
public class EquipmentInspectionClient extends BaseClient {

    private static final String API_PREFIX = "/equipments/inspection";
    private static final String DELIMITER = "/";

    @Autowired
    public EquipmentInspectionClient(@Value("${diagnosedEquipment-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewEquipmentInspectionDto inspectionDto) {
        return post(API_PREFIX, inspectionDto);
    }

    public Mono<Object> update(UpdateEquipmentInspectionDto inspectionDto) {
        return patch(API_PREFIX,inspectionDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}