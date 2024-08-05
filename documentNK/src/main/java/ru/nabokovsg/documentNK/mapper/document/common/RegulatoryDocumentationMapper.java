package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.model.document.common.RegulatoryDocumentation;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.template.common.DocumentationTemplate;

@Mapper(componentModel = "spring")
public interface RegulatoryDocumentationMapper {

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.documentName", target = "documentName")
    @Mapping(source = "subsection", target = "subsection")
    @Mapping(target = "id", ignore = true)
    RegulatoryDocumentation mapToRegulatoryDocumentation(DocumentationTemplate template
                                                       , Subsection subsection);
}