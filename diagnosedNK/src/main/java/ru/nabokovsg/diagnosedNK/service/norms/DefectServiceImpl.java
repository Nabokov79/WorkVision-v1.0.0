package ru.nabokovsg.diagnosedNK.service.norms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.defects.DefectDto;
import ru.nabokovsg.diagnosedNK.dto.norms.defects.ResponseDefectDto;
import ru.nabokovsg.diagnosedNK.dto.norms.defects.ResponseShortDefectDto;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.norms.DefectMapper;
import ru.nabokovsg.diagnosedNK.model.norms.*;
import ru.nabokovsg.diagnosedNK.repository.norms.DefectRepository;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final DefectRepository repository;
    private final DefectMapper mapper;
    private final MeasuredParameterService parameterService;
    private final ConstParameterMeasurementService constParameter;

    @Override
    public ResponseDefectDto save(DefectDto defectDto) {
        Defect defect = repository.findByDefectName(defectDto.getDefectName());
        if (defect == null) {
            defect = repository.save(mapper.mapToDefect(defectDto
                                                      , getTypeCalculation(defectDto.getCalculation())));
            defect.setMeasuredParameters(parameterService.saveForDefect(defect, defectDto.getMeasuredParameters()));
        }
        return mapper.mapToResponseDefectDto(defect);
    }

    @Override
    public ResponseDefectDto update(DefectDto defectDto) {
        if (repository.existsById(defectDto.getId())) {
            Defect defect = repository.save(mapper.mapToDefect(defectDto
                                                           , getTypeCalculation(defectDto.getCalculation())));
            defect.setMeasuredParameters(parameterService.update(defectDto.getMeasuredParameters()));
            return mapper.mapToResponseDefectDto(defect);
        }
        throw new NotFoundException(String.format("Defect with id=%s not found for update", defectDto.getId()));
    }

    @Override
    public ResponseDefectDto get(Long id) {
        return mapper.mapToResponseDefectDto(getById(id));
    }

    @Override
    public List<ResponseShortDefectDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToResponseShortDefectDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Defect with id=%s not found for delete", id));
    }

    @Override
    public Defect getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Defect with id=%s not found", id)));
    }

    private ParameterCalculationType getTypeCalculation(String calculation) {
        return ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported defect calculation type=%s", calculation)));
    }

    private void validateParameters(List<MeasuredParameterDto> measuredParameters, ParameterCalculationType calculation) {
        if (calculation.equals(ParameterCalculationType.SQUARE)) {
            String lengthName = constParameter.get(String.valueOf(MeasuredParameterType.LENGTH));
            String widthName = constParameter.get(String.valueOf(MeasuredParameterType.WIDTH));
            String diameterName = constParameter.get(String.valueOf(MeasuredParameterType.DIAMETER));
            boolean length = false;
            boolean width = false;
            boolean diameter = false;
            for (MeasuredParameterDto parameter : measuredParameters) {
                if (parameter.getMeasuredParameter().equals(lengthName)) {
                    length = true;
                }
                if (parameter.getMeasuredParameter().equals(widthName)) {
                    width = true;
                }
                if (parameter.getMeasuredParameter().equals(diameterName)) {
                    diameter = true;
                }
            }
            if (!length && !width && !diameter) {
                throw new BadRequestException(
                        String.format("Incorrect measurement parameters have been set for calculating the area" +
                                "                       , length=%s, width=%s, diameter=%s", length, width, diameter));
            }
        }
    }
}