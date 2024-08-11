package ru.nabokovsg.gateway.client.diagnosedNK.norms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair.NewElementRepairDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair.UpdateElementRepairDto;

@Service
public class ElementRepairClient extends BaseClient {

    private static final String API_PREFIX = "diagnosed/nk/norms/repair";
    private static final String DELIMITER = "/";

    @Autowired
    public ElementRepairClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewElementRepairDto repairDto) {
        return post(API_PREFIX, repairDto);
    }

    public Mono<Object> update(UpdateElementRepairDto repairDto) {
        return patch(API_PREFIX, repairDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }

    public Flux<Object> getAll() {
        return getAll(String.join(DELIMITER, API_PREFIX));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}