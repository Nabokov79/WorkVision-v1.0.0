package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.CalculatedPartElementRepository;

@Service
@RequiredArgsConstructor
public class CalculatedPartElementServiceImpl implements CalculatedPartElementService {

    private final CalculatedPartElementRepository repository;
}