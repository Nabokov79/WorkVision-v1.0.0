package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.remark.RemarkDto;
import ru.nabokovsg.laboratoryNK.dto.remark.ResponseRemarkDto;

import java.util.List;

public interface RemarkService {

    ResponseRemarkDto save(RemarkDto remarkDto);

    ResponseRemarkDto update(RemarkDto remarkDto);

    List<ResponseRemarkDto> getAll(Long id);

    void delete(Long id);
}