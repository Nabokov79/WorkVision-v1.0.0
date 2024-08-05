package ru.nabokovsg.documentNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.documentNK.dto.client.templateCreate.*;

import java.util.List;

@Component
public class CompanyClient {

    private final WebClient client;

    @Autowired
    public CompanyClient(@Qualifier(value = "webCompany") WebClient client) {
        this.client = client;
    }

    public OrganizationDto getOrganization(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();
    }

    public BranchDto getBranch(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(BranchDto.class)
                .block();
    }

    public DepartmentDto getDepartment(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
    }

    public HeatSupplyAreaDto getHeatSupplyArea(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(HeatSupplyAreaDto.class)
                .block();
    }

    public ExploitationRegionDto getExploitationRegion(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ExploitationRegionDto.class)
                .block();
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
}