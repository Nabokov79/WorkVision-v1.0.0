package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.organization.NewOrganizationDto;
import ru.nabokovsg.gateway.dto.company.organization.UpdateOrganizationDto;

@Service
public class OrganizationClient extends BaseClient {

    private static final String API_PREFIX = "/organization";
    private static final String DELIMITER = "/";

    @Autowired
    public OrganizationClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewOrganizationDto organizationDto) {
        return post(API_PREFIX, organizationDto);
    }

    public Mono<Object> update(UpdateOrganizationDto organizationDto) {
        return patch(API_PREFIX, organizationDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }

    public Flux<Object> getAll() {
        return getAll(API_PREFIX);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}