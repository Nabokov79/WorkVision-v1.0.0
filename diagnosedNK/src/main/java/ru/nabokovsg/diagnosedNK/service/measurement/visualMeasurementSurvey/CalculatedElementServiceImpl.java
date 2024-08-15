package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.CalculatedElementRepository;

@Service
@RequiredArgsConstructor
public class CalculatedElementServiceImpl implements CalculatedElementService {

    private final CalculatedElementRepository repository;
}