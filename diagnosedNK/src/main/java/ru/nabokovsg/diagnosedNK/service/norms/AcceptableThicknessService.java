package ru.nabokovsg.diagnosedNK.service.norms;

import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.AcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableThickness.ResponseAcceptableThicknessDto;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableThickness;

import java.util.List;

public interface AcceptableThicknessService {

    ResponseAcceptableThicknessDto save(AcceptableThicknessDto thicknessDto);

    ResponseAcceptableThicknessDto update(AcceptableThicknessDto thicknessDto);

    List<ResponseAcceptableThicknessDto> getAll(Long equipmentTypeId);

    void delete(Long id);

    AcceptableThickness getByPredicate(Long equipmentTypeId, Long elementId, Long partElementId, Integer diameter);
}