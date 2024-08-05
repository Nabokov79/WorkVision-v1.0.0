package ru.nabokovsg.laboratoryNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.laboratoryNK.dto.client.EquipmentDto;

@Component
public class DiagnosticObjectNKClient {

    private final WebClient client;

    @Autowired
    public DiagnosticObjectNKClient(@Qualifier(value = "webDiagnosticObjectNK") WebClient client) {
        this.client = client;
    }

    public EquipmentDto getEquipment(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(EquipmentDto.class)
                .block();
    }
}
