package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.DeviationYearMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.DeviationYearRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviationYearServiceImpl implements DeviationYearService {

    private final DeviationYearRepository repository;
    private final DeviationYearMapper mapper;

    @Override
    public void save(List<ReferencePoint> points) {
        List<Long> referencePointIds = points.stream().map(ReferencePoint::getId).toList();
        Map<Long, ReferencePoint> referencePoints = points.stream()
                .collect(Collectors.toMap(ReferencePoint::getId, r -> r));
        int year = LocalDate.now().getYear();
        repository.saveAll(repository.findAllByReferencePointId(referencePointIds)
                .stream()
                .map(d -> {
                    ReferencePoint point = referencePoints.get(d.getReferencePoint().getId());
                    if (year == d.getYear()) {
                        return mapper.mapToDeviationYear(year, point);
                    } else {
                        return d;
                    }
                })
                .toList()
        );
    }

    @Override
    public void update(List<ReferencePoint> points) {
        List<Long> referencePointIds = points.stream().map(ReferencePoint::getId).toList();
        Map<Long, ReferencePoint> referencePoints = points.stream()
                                                           .collect(Collectors.toMap(ReferencePoint::getId, r -> r));
        int year = LocalDate.now().getYear();
        repository.saveAll(repository.findAllByReferencePointId(referencePointIds)
                .stream()
                .map(d -> {
                    ReferencePoint point = referencePoints.get(d.getReferencePoint().getId());
                    if (year == d.getYear()) {
                        return mapper.mapToUpdateDeviationYear(year, point, d.getId());
                    } else {
                        return d;
                    }
                })
                .toList()
        );
    }
}