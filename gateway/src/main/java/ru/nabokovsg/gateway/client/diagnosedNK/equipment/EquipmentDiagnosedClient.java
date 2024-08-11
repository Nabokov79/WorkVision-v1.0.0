package ru.nabokovsg.gateway.client.diagnosedNK.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentDiagnosed.NewEquipmentDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentDiagnosed.UpdateEquipmentDto;

@Service
public class EquipmentDiagnosedClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/equipments";
    private static final String DELIMITER = "/";

    @Autowired
    public EquipmentDiagnosedClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }


    public Mono<Object> save(NewEquipmentDto equipmentDto) {
        return post(API_PREFIX, equipmentDto);
    }

    public Mono<Object> update(UpdateEquipmentDto equipmentDto) {
        return patch(API_PREFIX, equipmentDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long buildingId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(buildingId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}