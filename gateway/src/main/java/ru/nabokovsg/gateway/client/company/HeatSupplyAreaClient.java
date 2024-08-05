package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.heatSupplyArea.NewHeatSupplyAreaDto;
import ru.nabokovsg.gateway.dto.company.heatSupplyArea.UpdateHeatSupplyAreaDto;

@Service
public class HeatSupplyAreaClient extends BaseClient {

    private static final String API_PREFIX_HEAT_SUPPLY_AREA = "/heat/supply/area";
    private static final String DELIMITER = "/";

    @Autowired
    public HeatSupplyAreaClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewHeatSupplyAreaDto areaDto) {
        return post(API_PREFIX_HEAT_SUPPLY_AREA, areaDto);
    }

    public Mono<Object> update(UpdateHeatSupplyAreaDto areaDto) {
        return patch(API_PREFIX_HEAT_SUPPLY_AREA, areaDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX_HEAT_SUPPLY_AREA, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long id) {
        return getAll(String.join(DELIMITER, API_PREFIX_HEAT_SUPPLY_AREA, "all", String.valueOf(id)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX_HEAT_SUPPLY_AREA, String.valueOf(id)));
    }
}