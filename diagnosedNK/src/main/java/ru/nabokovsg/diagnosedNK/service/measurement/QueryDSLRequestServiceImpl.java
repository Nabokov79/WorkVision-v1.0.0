package ru.nabokovsg.diagnosedNK.service.measurement;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.equipment.*;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.*;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.*;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.*;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QueryDSLRequestServiceImpl implements QueryDSLRequestService {

    private final EntityManager em;

    @Override
    public IdentifiedDefect getIdentifiedDefect(IdentifiedDefectDto defectDto) {
        QIdentifiedDefect defect = QIdentifiedDefect.identifiedDefect;
        BooleanBuilder builder = getPredicate(defectDto.getEquipmentId()
                                            , defectDto.getElementId()
                                            , defectDto.getPartElementId());
        builder.and(defect.defectId.eq(defectDto.getDefectId()));
        return new JPAQueryFactory(em).from(defect)
                .select(defect)
                .where(builder)
                .fetchOne();
    }

    @Override
    public CompletedRepair getCompletedRepair(CompletedRepairDto repairDto) {
        QCompletedRepair repair = QCompletedRepair.completedRepair;
        BooleanBuilder builder = getPredicate(repairDto.getEquipmentId()
                                            , repairDto.getElementId()
                                            , repairDto.getPartElementId());
        builder.and(repair.repairId.eq(repairDto.getRepairId()));
        return new JPAQueryFactory(em).from(repair)
                .select(repair)
                .where(builder)
                .fetchOne();
    }

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
        return new HashSet<>(new JPAQueryFactory(em)
                .from(pointDifference)
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
        return new JPAQueryFactory(em)
                            .from(measurement)
                            .select(measurement)
                            .where(builder)
                            .fetchOne();
    }

    private BooleanBuilder getPredicate(Long equipmentId, Long elementId, Long partElementId) {
        QVisualMeasuringSurvey vms = QVisualMeasuringSurvey.visualMeasuringSurvey;
        QExaminedPartElement part = QExaminedPartElement.examinedPartElement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(vms.equipmentId.eq(equipmentId));
        builder.and(vms.elementId.eq(elementId));
        if (partElementId != null) {
            builder.and(part.partElementId.eq(partElementId));
        }
        return builder;
    }
}