package ru.nabokovsg.documentNK.service.document.protocol;

import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.InformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.ResponseInformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;

import java.util.List;

public interface InformationAboutObjectOfControlService {

    ResponseInformationAboutObjectOfControlDto save(InformationAboutObjectOfControlDto informationDto);

    ResponseInformationAboutObjectOfControlDto update(InformationAboutObjectOfControlDto informationDto);

   List<ResponseInformationAboutObjectOfControlDto> getAll(Long protocolId);

   void addProtocolControl(ProtocolControl protocol, Long workJournalId);

    void delete(Long id);
}