package ru.nabokovsg.gateway.client.documentNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate.NewInformationObjectOfControlTemplateDto;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate.UpdateInformationObjectOfControlTemplateDto;

@Service
public class InformationAboutObjectOfControlTemplateClient extends BaseClient {

    private static final String API_PREFIX = "/template/protocol/control/object/information";
    private static final String DELIMITER = "/";

    @Autowired
    public InformationAboutObjectOfControlTemplateClient(@Value("${documentNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewInformationObjectOfControlTemplateDto templateDto) {
        return post(API_PREFIX, templateDto);
    }

    public Mono<Object> update(UpdateInformationObjectOfControlTemplateDto templateDto) {
        return patch(API_PREFIX, templateDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long documentTypeId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(documentTypeId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}