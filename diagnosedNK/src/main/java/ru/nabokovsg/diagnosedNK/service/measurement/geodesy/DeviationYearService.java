package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;

import java.util.List;

public interface DeviationYearService {

    void save(List<ReferencePoint> points);

    void update(List<ReferencePoint> points);
}