package ru.nabokovsg.gateway.client.diagnosedNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm.NewDefectDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm.UpdateDefectDto;

@Service
public class DefectClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/norms/defects";
    private static final String DELIMITER = "/";

    @Autowired
    public DefectClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewDefectDto defectDto) {
        return post(API_PREFIX, defectDto);
    }

    public Mono<Object> update(UpdateDefectDto defectDto) {
       return patch(API_PREFIX, defectDto);
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