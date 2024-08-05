package ru.nabokovsg.diagnosedNK.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.common.RecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.dto.common.ResponseRecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.common.RecommendationEmployeeMapper;
import ru.nabokovsg.diagnosedNK.repository.common.RecommendationEmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationEmployeeServiceImpl implements RecommendationEmployeeService {

    private final RecommendationEmployeeRepository repository;
    private final RecommendationEmployeeMapper mapper;

    @Override
    public ResponseRecommendationEmployeeDto save(RecommendationEmployeeDto recommendationDto) {
        if (repository.existsByEquipmentIdAndRecommendation(recommendationDto.getEquipmentId()
                                                          , recommendationDto.getRecommendation())) {
            throw new NotFoundException(
                    String.format("RecommendationEmployee recommendation=%s is found"
                                                                             , recommendationDto.getRecommendation()));
        }
        return mapper.mapToResponseRecommendationEmployeeDto(
                repository.save(mapper.mapToRecommendationEmployee(recommendationDto)));
    }

    @Override
    public ResponseRecommendationEmployeeDto update(RecommendationEmployeeDto recommendationDto) {
        if (repository.existsById(recommendationDto.getId())) {
            return mapper.mapToResponseRecommendationEmployeeDto(
                    repository.save(mapper.mapToRecommendationEmployee(recommendationDto)));
        }
        throw new NotFoundException(
                String.format("RecommendationEmployee with id=%s not found for update", recommendationDto.getId()));
    }

    @Override
    public List<ResponseRecommendationEmployeeDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                         .stream()
                         .map(mapper::mapToResponseRecommendationEmployeeDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("RecommendationEmployee with id=%s not found for delete", id));
    }
}