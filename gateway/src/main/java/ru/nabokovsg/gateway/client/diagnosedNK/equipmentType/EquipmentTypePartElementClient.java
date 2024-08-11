package ru.nabokovsg.gateway.client.diagnosedNK.equipmentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypePartElement.NewEquipmentTypePartElementDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypePartElement.UpdateEquipmentTypePartElementDto;

@Service
public class EquipmentTypePartElementClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/equipments/type/element/part";
    private static final String DELIMITER = "/";

    @Autowired
    public EquipmentTypePartElementClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewEquipmentTypePartElementDto partElementDto) {
        return post(API_PREFIX, partElementDto);
    }

    public Mono<Object> update(UpdateEquipmentTypePartElementDto partElementDto) {
        return patch(API_PREFIX, partElementDto);
    }

    public Flux<Object> getAll(Long elementId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(elementId)));
    }
}