package ru.nabokovsg.diagnosedNK.service.common;

import ru.nabokovsg.diagnosedNK.dto.common.RecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.dto.common.ResponseRecommendationEmployeeDto;

import java.util.List;

public interface RecommendationEmployeeService {

    ResponseRecommendationEmployeeDto save(RecommendationEmployeeDto recommendationDto);

   ResponseRecommendationEmployeeDto update(RecommendationEmployeeDto recommendationDto);

    List<ResponseRecommendationEmployeeDto> getAll(Long equipmentId);

    void delete(Long id);
}