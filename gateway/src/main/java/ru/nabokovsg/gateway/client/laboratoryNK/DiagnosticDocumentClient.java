package ru.nabokovsg.gateway.client.laboratoryNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiagnosticDocumentClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/document";
    private static final String DELIMITER = "/";

    @Autowired
    public DiagnosticDocumentClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Flux<Object> getAll(LocalDate startPeriod, LocalDate endPeriod, boolean week, boolean month) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (startPeriod != null) {
            params.put("startPeriod", List.of(String.valueOf(startPeriod)));
        }
        if (startPeriod != null) {
            params.put("endPeriod", List.of(String.valueOf(endPeriod)));
        }
        if (startPeriod != null) {
            params.put("week", List.of(String.valueOf(week)));
        }
        if (startPeriod != null) {
            params.put("month", List.of(String.valueOf(month)));
        }
        return getAll(String.join(DELIMITER, API_PREFIX, "all"), params);
    }

    public Mono<Object> create(Long workJournalId) {
        return get(String.join(DELIMITER, API_PREFIX, "/create", String.valueOf(workJournalId)));
    }
}