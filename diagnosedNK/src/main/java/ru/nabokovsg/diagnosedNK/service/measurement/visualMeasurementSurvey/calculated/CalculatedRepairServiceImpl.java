package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedRepairRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculatedRepairServiceImpl implements CalculatedRepairService {

    private final CalculatedRepairRepository repository;

    @Override
    public void save(Set<CompletedRepair> repairs
                   , CompletedRepair repair
                   , CalculationDefectOrRepair calculation
                   , Set<MeasuredParameter> measuredParameters) {

    }

    @Override
    public void update(List<CompletedRepair> repairs
                     , CompletedRepair repair
                     , CalculationDefectOrRepair calculation
                     , Set<MeasuredParameter> measuredParameters) {

    }
}