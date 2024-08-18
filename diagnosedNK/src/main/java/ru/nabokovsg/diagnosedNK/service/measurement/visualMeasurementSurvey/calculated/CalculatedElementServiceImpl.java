package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedElementRepository;

@Service
@RequiredArgsConstructor
public class CalculatedElementServiceImpl implements CalculatedElementService {

    private final CalculatedElementRepository repository;
}