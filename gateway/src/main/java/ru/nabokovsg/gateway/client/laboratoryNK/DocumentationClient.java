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
import ru.nabokovsg.gateway.dto.laboratoryNK.documentation.NewDocumentationDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentation.UpdateDocumentationDto;

import java.util.List;

@Service
public class DocumentationClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/documentation";
    private static final String DELIMITER = "/";

    @Autowired
    public DocumentationClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewDocumentationDto documentationDto) {
        return post(API_PREFIX, documentationDto);
    }

    public Mono<Object> update(UpdateDocumentationDto documentationDto) {
        return patch(API_PREFIX, documentationDto);
    }

    public Flux<Object> getAll(String number, String title) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (number != null) {
            params.put(number, List.of(number));
        }
        if (title != null) {
            params.put(title, List.of(title));
        }
        return getAll(String.join(DELIMITER, API_PREFIX, "all"), params);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}