package ru.nabokovsg.gateway.client.diagnosedNK.norms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesyNorm.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesyNorm.UpdateAcceptableDeviationsGeodesyDto;

@Service
public class AcceptableDeviationsGeodesyClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/norms/geodesy";
    private static final String DELIMITER = "/";

    @Autowired
    public AcceptableDeviationsGeodesyClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewAcceptableDeviationsGeodesyDto geodesyDto) {
        return post(API_PREFIX, geodesyDto);
    }

    public Mono<Object> update(UpdateAcceptableDeviationsGeodesyDto geodesyDto) {
        return patch(API_PREFIX, geodesyDto);
    }

    public Flux<Object> getAll(Long equipmentTypeId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentTypeId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}