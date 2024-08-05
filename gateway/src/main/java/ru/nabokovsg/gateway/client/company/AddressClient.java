package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.address.NewAddressDto;
import ru.nabokovsg.gateway.dto.company.address.UpdateAddressDto;

@Service
public class AddressClient extends BaseClient {

    private static final String API_PREFIX = "/address";
    private static final String DELIMITER = "/";

    @Autowired
    public AddressClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewAddressDto addressDto) {
        return post(API_PREFIX, addressDto);
    }

    public Mono<Object> update(UpdateAddressDto addressDto) {
        return patch(API_PREFIX, addressDto);
    }

    public Flux<Object> getAll() {
        return getAll(API_PREFIX);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}