package ru.nabokovsg.gateway.client.diagnosedNK.norms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.acceptableThickness.NewAcceptableThicknessDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.acceptableThickness.UpdateAcceptableThicknessDto;

@Service
public class AcceptableThicknessClient extends BaseClient {


    private static final String API_PREFIX = "/diagnosed/nk/norms/thickness";
    private static final String DELIMITER = "/";

    @Autowired
    public AcceptableThicknessClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewAcceptableThicknessDto thicknessDto) {
        return post(API_PREFIX, thicknessDto);
    }

    public Mono<Object> update(UpdateAcceptableThicknessDto thicknessDto) {
        return patch(API_PREFIX, thicknessDto);
    }

    public Flux<Object> getAll(Long equipmentTypeId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentTypeId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}