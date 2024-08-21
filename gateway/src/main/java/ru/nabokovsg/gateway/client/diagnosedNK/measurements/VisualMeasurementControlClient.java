package ru.nabokovsg.gateway.client.diagnosedNK.measurements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.visualMeasurementControl.UpdateVisualMeasurementControlDto;

@Service
public class VisualMeasurementControlClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/control/visual";
    private static final String DELIMITER = "/";

    @Autowired
    public VisualMeasurementControlClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewVisualMeasurementControlDto defectDto) {
        return post(API_PREFIX, defectDto);
    }

    public Mono<Object> update(UpdateVisualMeasurementControlDto defectDto) {
        return patch(API_PREFIX, defectDto);
    }

    public Flux<Object> getAll(Long workJournalId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(workJournalId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}
