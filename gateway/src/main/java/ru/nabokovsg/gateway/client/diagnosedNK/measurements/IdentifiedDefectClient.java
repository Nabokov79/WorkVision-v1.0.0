package ru.nabokovsg.gateway.client.diagnosedNK.measurements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.identifiedDefect.NewIdentifiedDefectDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.identifiedDefect.UpdateIdentifiedDefectDto;
import ru.nabokovsg.gateway.exceptions.BadRequestException;

@Service
public class IdentifiedDefectClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/defect";
    private static final String DELIMITER = "/";

    @Autowired
    public IdentifiedDefectClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewIdentifiedDefectDto defectDto) {
        return post(API_PREFIX, defectDto);
    }

    public Mono<Object> update(UpdateIdentifiedDefectDto defectDto) {
        return patch(API_PREFIX, defectDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }

    public Mono<String> delete(Long id, Integer quantity) {
        if (quantity != null && (quantity == 0 || quantity < 0)) {
            throw new BadRequestException(String.format("Parameter quantity=%s can only be positive", quantity));
        }
        if (quantity == null) {
            return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
        }
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id))
                ,"quantity", String.valueOf(quantity));
    }
}