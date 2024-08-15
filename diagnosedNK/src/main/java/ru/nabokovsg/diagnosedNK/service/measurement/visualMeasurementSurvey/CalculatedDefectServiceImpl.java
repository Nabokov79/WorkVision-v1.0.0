package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.CalculatedDefectRepository;

@Service
@RequiredArgsConstructor
public class CalculatedDefectServiceImpl implements CalculatedDefectService {

    private final CalculatedDefectRepository repository;
}