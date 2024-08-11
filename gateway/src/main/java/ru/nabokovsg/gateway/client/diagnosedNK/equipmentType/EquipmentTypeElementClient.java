package ru.nabokovsg.gateway.client.diagnosedNK.equipmentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypeElement.NewEquipmentTypeElementDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypeElement.UpdateEquipmentTypeElementDto;

@Service
public class EquipmentTypeElementClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/equipments/type/element";
    private static final String DELIMITER = "/";

    @Autowired
    public EquipmentTypeElementClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewEquipmentTypeElementDto elementDto) {
        return post(API_PREFIX, elementDto);
    }

    public Mono<Object> update(UpdateEquipmentTypeElementDto elementDto) {
        return patch(API_PREFIX, elementDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }
}