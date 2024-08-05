package ru.nabokovsg.documentNK.mapper.document.protocol;

import org.mapstruct.Mapper;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.protocol.Workplace;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {

    Workplace mapToWorkplace(DocumentCreationDataDto journal);
}