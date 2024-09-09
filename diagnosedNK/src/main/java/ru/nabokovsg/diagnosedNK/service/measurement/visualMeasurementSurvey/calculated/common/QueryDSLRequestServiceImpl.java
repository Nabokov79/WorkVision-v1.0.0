package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.common;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.*;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.*;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.*;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class QueryDSLRequestServiceImpl implements QueryDSLRequestService {

    private final EntityManager em;

    @Override
    public Long getEquipmentTypeId(Long elementId) {
        QEquipmentDiagnosed equipment = QEquipmentDiagnosed.equipmentDiagnosed;
        return new JPAQueryFactory(em).from(equipment)
                .select(equipment.equipmentTypeId)
                .where(QEquipmentElement.equipmentElement.id.eq(elementId))
                .fetchOne();
    }

    @Override
    public Set<ReferencePoint> getAllReferencePoint(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QReferencePoint referencePoint = QReferencePoint.referencePoint;
        return new HashSet<>(new JPAQueryFactory(em).from(referencePoint)
                .select(referencePoint)
                .innerJoin(referencePoint.geodesicMeasurements, geodesic)
                .where(geodesic.equipmentId.eq(equipmentId))
                .fetch());
    }

    @Override
    public Set<ControlPoint> getAllControlPoint(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QControlPoint controlPoint = QControlPoint.controlPoint;
        return new HashSet<>(new JPAQueryFactory(em).from(controlPoint)
                .select(controlPoint)
                .innerJoin(controlPoint.geodesicMeasurements, geodesic)
                .where(geodesic.equipmentId.eq(equipmentId))
                .fetch());
    }

    @Override
    public Set<PointDifference> getAllPointDifference(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QPointDifference pointDifference = QPointDifference.pointDifference;
        return new HashSet<>(new JPAQueryFactory(em).from(pointDifference)
                .select(pointDifference)
                .innerJoin(pointDifference.geodesicMeasurements, geodesic)
                .where(geodesic.equipmentId.eq(equipmentId))
                .fetch());
    }

    @Override
    public UltrasonicThicknessMeasurement getUltrasonicThicknessMeasurement(UTPredicateData predicateData) {
        QUltrasonicThicknessMeasurement measurement = QUltrasonicThicknessMeasurement
                .ultrasonicThicknessMeasurement;
        QUltrasonicThicknessElementMeasurement element = QUltrasonicThicknessElementMeasurement
                .ultrasonicThicknessElementMeasurement;
        QUltrasonicThicknessPartElementMeasurement partElement = QUltrasonicThicknessPartElementMeasurement
                .ultrasonicThicknessPartElementMeasurement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(element.equipmentId.eq(predicateData.getEquipmentId()));
        builder.and(element.elementId.eq(predicateData.getElementId()));
        if (predicateData.getPartElementId() != null) {
            builder.and(partElement.partElementId.eq(predicateData.getPartElementId()));
        }
        builder.and(measurement.measurementNumber.eq(predicateData.getMeasurementNumber()));
        return new JPAQueryFactory(em).from(measurement)
                .select(measurement)
                .where(builder)
                .fetchOne();
    }

    @Override
    public Double getMaxCorrosionValueByPredicate(UltrasonicThicknessMeasurementDto measurementDto, Long equipmentId) {
        QIdentifiedDefect defect = QIdentifiedDefect.identifiedDefect;
        QParameterMeasurement parameter = QParameterMeasurement.parameterMeasurement;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(defect.equipmentId.eq(equipmentId));
        booleanBuilder.and(defect.elementId.eq(measurementDto.getElementId()));
        booleanBuilder.and(defect.useCalculateThickness.eq(true));
        if (measurementDto.getPartElementId() != null) {
            booleanBuilder.and(defect.partElementId.eq(measurementDto.getPartElementId()));
        }
        booleanBuilder.and(parameter.identifiedDefect.defectId.eq(defect.id));
        Double corrosion = new JPAQueryFactory(em).from(parameter)
                .select(parameter.value)
                .where(booleanBuilder)
                .fetchFirst();
        if (corrosion == null) {
            throw new NotFoundException(String.format("Max corrosion value not found corrosion=%s", corrosion));
        }
        return corrosion;
    }
}