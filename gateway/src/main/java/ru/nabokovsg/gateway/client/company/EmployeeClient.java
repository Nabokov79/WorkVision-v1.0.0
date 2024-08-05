package ru.nabokovsg.gateway.client.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.company.employee.NewEmployeeDto;
import ru.nabokovsg.gateway.dto.company.employee.UpdateEmployeeDto;

@Service
public class EmployeeClient extends BaseClient {

    private static final String API_PREFIX_EMPLOYEE = "/employee";
    private static final String DELIMITER = "/";

    @Autowired
    public EmployeeClient(@Value("${company-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewEmployeeDto employeeDto) {
        return post(API_PREFIX_EMPLOYEE, employeeDto);
    }

    public Mono<Object> update(UpdateEmployeeDto employeeDto) {
        return patch(API_PREFIX_EMPLOYEE, employeeDto);
    }

    public Mono<Object> get(Long id) {
        return get(String.join(DELIMITER, API_PREFIX_EMPLOYEE, String.valueOf(id)));
    }

    public Flux<Object> getAll(Long id, String divisionType) {
        return getAll(String.join(DELIMITER,  API_PREFIX_EMPLOYEE, "all", String.valueOf(id))
                , "divisionType", divisionType);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX_EMPLOYEE, String.valueOf(id)));
    }
}