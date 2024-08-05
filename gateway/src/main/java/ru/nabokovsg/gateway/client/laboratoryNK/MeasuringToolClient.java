package ru.nabokovsg.gateway.client.laboratoryNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.NewMeasuringToolDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.SearchParameters;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.UpdateMeasuringToolDto;

import java.util.List;

@Service
public class MeasuringToolClient extends BaseClient {

    private static final String API_PREFIX = "/laboratory/nk/instrument";
    private static final String DELIMITER = "/";

    @Autowired
    public MeasuringToolClient(@Value("${laboratoryNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewMeasuringToolDto measuringToolDto) {
        return post(API_PREFIX, measuringToolDto);
    }

    public Mono<Object> update(UpdateMeasuringToolDto measuringToolDto) {
        return patch(API_PREFIX, measuringToolDto);
    }

    public Flux<Object> getAll(SearchParameters searchParameter) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
       if (searchParameter.getToll() != null) {
           params.put(searchParameter.getToll(), List.of(searchParameter.getToll()));
       }
       if (searchParameter.getModel() != null) {
           params.put(searchParameter.getModel(), List.of(searchParameter.getModel()));
       }
        if (searchParameter.getWorkNumber() != null) {
            params.put(searchParameter.getWorkNumber(), List.of(searchParameter.getWorkNumber()));
        }
        if (searchParameter.getManufacturing() != null) {
            params.put(String.valueOf(searchParameter.getManufacturing())
                     , List.of(String.valueOf(searchParameter.getManufacturing())));
        }
        if (searchParameter.getExploitation() != null) {
            params.put(String.valueOf(searchParameter.getExploitation())
                    , List.of(String.valueOf(searchParameter.getExploitation())));
        }
        if (searchParameter.getManufacturer() != null) {
            params.put(searchParameter.getManufacturer(), List.of(searchParameter.getManufacturer()));
        }
        if (searchParameter.getOrganization() != null) {
            params.put(searchParameter.getOrganization(), List.of(searchParameter.getOrganization()));
        }
        if (searchParameter.getControlType() != null) {
            params.put(searchParameter.getControlType(), List.of(searchParameter.getControlType()));
        }
        if (searchParameter.getEmployeeId() != null) {
            params.put(String.valueOf(searchParameter.getControlType())
                      , List.of(String.valueOf(searchParameter.getControlType())));
        }
        return getAll(String.join(DELIMITER, API_PREFIX, "/all"), params);
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}