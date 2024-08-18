package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedRepairRepository;

@Service
@RequiredArgsConstructor
public class CalculatedRepairServiceImpl implements CalculatedRepairService {

    private final CalculatedRepairRepository repository;
}