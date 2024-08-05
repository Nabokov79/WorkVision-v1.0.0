package ru.nabokovsg.gateway.client.laboratoryNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentRemark.NewRemarkDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentRemark.UpdateRemarkDto;

@Service
public class RemarkClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/document/remark";
    private static final String DELIMITER = "/";

    @Autowired
    public RemarkClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewRemarkDto remarkDto) {
        return post(API_PREFIX, remarkDto);
    }

    public Mono<Object> update(UpdateRemarkDto remarkDto) {
        return patch(API_PREFIX, remarkDto);
    }

    public Flux<Object> getAll(Long id) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(id)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}