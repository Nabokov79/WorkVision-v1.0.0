package ru.nabokovsg.laboratoryNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.laboratoryNK.dto.client.BranchDto;
import ru.nabokovsg.laboratoryNK.dto.client.EmployeeDto;

import java.util.List;

@Component
public class CompanyClient {

    private final WebClient client;

    @Autowired
    public CompanyClient(@Qualifier(value = "webCompany") WebClient client) {
        this.client = client;
    }

    public List<EmployeeDto> getAllEmployees(String path, String paramName, String param) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam(paramName, param)
                        .build())
                .retrieve()
                .bodyToFlux(EmployeeDto.class)
                .buffer()
                .blockFirst();
    }

    public BranchDto getBranch(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(BranchDto.class)
                .block();
    }
}