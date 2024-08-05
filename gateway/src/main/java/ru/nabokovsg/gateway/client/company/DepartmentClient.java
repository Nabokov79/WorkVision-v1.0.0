package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.department.NewDepartmentDto;
import ru.nabokovsg.gateway.dto.company.department.UpdateDepartmentDto;

@Service
public class DepartmentClient extends BaseClient {

    private static final String API_PREFIX_DEPARTMENT = "/department";
    private static final String DELIMITER = "/";

    @Autowired
    public DepartmentClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewDepartmentDto departmentDto) {
        return post(API_PREFIX_DEPARTMENT, departmentDto);
    }

    public Mono<Object> update(UpdateDepartmentDto departmentDto) {
        return patch(API_PREFIX_DEPARTMENT, departmentDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX_DEPARTMENT, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long id) {
        return getAll(String.join(DELIMITER, API_PREFIX_DEPARTMENT, "all", String.valueOf(id)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX_DEPARTMENT, String.valueOf(id)));
    }
}