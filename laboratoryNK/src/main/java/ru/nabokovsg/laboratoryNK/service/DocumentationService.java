package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.documentation.DocumentationDto;
import ru.nabokovsg.laboratoryNK.model.Documentation;

import java.util.List;

public interface DocumentationService {

    DocumentationDto save(DocumentationDto documentationDto);

    DocumentationDto update(DocumentationDto documentationDto);

    DocumentationDto get(Long id);

    List<DocumentationDto> getAll(String number, String title);

    Documentation getById(Long id);

   void delete(Long id);
}