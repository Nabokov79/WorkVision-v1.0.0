package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.branch.NewBranchDto;
import ru.nabokovsg.gateway.dto.company.branch.UpdateBranchDto;

@Service
public class BranchClient extends BaseClient {

    private static final String API_PREFIX_BRANCH = "/branch";
    private static final String DELIMITER = "/";

    @Autowired
    public BranchClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewBranchDto branchDto) {
        return post(API_PREFIX_BRANCH, branchDto);
    }

    public Mono<Object> update(UpdateBranchDto branchDto) {
        return patch(API_PREFIX_BRANCH, branchDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX_BRANCH, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long id) {
        return getAll(String.join(DELIMITER, API_PREFIX_BRANCH, "all", String.valueOf(id)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX_BRANCH, String.valueOf(id)));
    }
}