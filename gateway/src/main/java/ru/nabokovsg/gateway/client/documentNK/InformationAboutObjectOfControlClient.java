package ru.nabokovsg.gateway.client.documentNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl.NewInformationAboutObjectOfControlDto;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl.UpdateInformationAboutObjectOfControlDto;

@Service
public class InformationAboutObjectOfControlClient extends BaseClient {

    private static final String API_PREFIX = "/document/protocol/control/information/object";
    private static final String DELIMITER = "/";

    @Autowired
    public InformationAboutObjectOfControlClient(@Value("${documentNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewInformationAboutObjectOfControlDto informationDto) {
        return patch(API_PREFIX, informationDto);
    }

    public Mono<Object> update(UpdateInformationAboutObjectOfControlDto informationDto) {
        return patch(API_PREFIX, informationDto);
    }

    public Flux<Object> getAll(Long protocolId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(protocolId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}