package ru.nabokovsg.gateway.client.laboratoryNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.employeeCertificate.NewEmployeeCertificateDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.employeeCertificate.UpdateEmployeeCertificateDto;

@Service
public class EmployeeCertificateClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/employee/certificates";
    private static final String DELIMITER = "/";

    @Autowired
    public EmployeeCertificateClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewEmployeeCertificateDto certificateDto) {
        return post(API_PREFIX, certificateDto);
    }

    public Mono<Object> update(UpdateEmployeeCertificateDto certificateDto) {
        return patch(API_PREFIX, certificateDto);
    }

    public Flux<Object> getAll(Long employeeId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(employeeId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}