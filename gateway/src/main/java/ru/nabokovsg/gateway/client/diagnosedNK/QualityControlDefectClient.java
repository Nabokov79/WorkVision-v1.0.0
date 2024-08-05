package ru.nabokovsg.gateway.client.diagnosedNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.qualityControlDefect.NewQualityControlDefectDto;

@Service
public class QualityControlDefectClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/defect";
    private static final String DELIMITER = "/";

    @Autowired
    public QualityControlDefectClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewQualityControlDefectDto defectDto) {
        return post(API_PREFIX, defectDto);
    }

    public Flux<Object> getAll(Long workJournalId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(workJournalId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}
