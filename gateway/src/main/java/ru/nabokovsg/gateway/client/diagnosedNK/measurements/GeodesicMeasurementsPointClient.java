package ru.nabokovsg.gateway.client.diagnosedNK.measurements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesy.NewGeodesicMeasurementsPointDto;

@Service
public class GeodesicMeasurementsPointClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/geodesy";
    private static final String DELIMITER = "/";

    @Autowired
    public GeodesicMeasurementsPointClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Flux<Object> save(NewGeodesicMeasurementsPointDto measurementDto) {
        return postAll(API_PREFIX, measurementDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}