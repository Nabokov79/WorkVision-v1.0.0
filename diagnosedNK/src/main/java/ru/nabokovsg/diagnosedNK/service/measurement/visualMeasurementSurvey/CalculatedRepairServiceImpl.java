package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.CalculatedRepairRepository;

@Service
@RequiredArgsConstructor
public class CalculatedRepairServiceImpl implements CalculatedRepairService {

    private final CalculatedRepairRepository repository;
}