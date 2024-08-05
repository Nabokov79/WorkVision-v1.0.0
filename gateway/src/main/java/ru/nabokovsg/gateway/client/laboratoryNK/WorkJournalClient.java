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
import ru.nabokovsg.gateway.dto.laboratoryNK.workJournal.NewWorkJournalDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.workJournal.UpdateWorkJournalDto;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkJournalClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/journal/work";
    private static final String DELIMITER = "/";

    @Autowired
    public WorkJournalClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewWorkJournalDto journalDto) {
        return post(API_PREFIX, journalDto);
    }

    public Mono<Object> update(UpdateWorkJournalDto journalDto) {
        return patch(API_PREFIX, journalDto);
    }

    public Flux<Object> getAll(LocalDate startPeriod, LocalDate endPeriod) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (startPeriod != null) {
            params.put(String.valueOf(startPeriod), List.of(String.valueOf(startPeriod)));
        }
        if (endPeriod != null) {
            params.put(String.valueOf(endPeriod), List.of(String.valueOf(endPeriod)));
        }
        return getAll(String.join(DELIMITER, API_PREFIX), params);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}
