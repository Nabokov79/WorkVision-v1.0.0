package ru.nabokovsg.diagnosedNK.mapper.common;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.common.RecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.dto.common.ResponseRecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.model.common.RecommendationEmployee;

@Mapper(componentModel = "spring")
public interface RecommendationEmployeeMapper {

    RecommendationEmployee mapToRecommendationEmployee(RecommendationEmployeeDto recommendationDto);

    ResponseRecommendationEmployeeDto mapToResponseRecommendationEmployeeDto(RecommendationEmployee recommendation);
}