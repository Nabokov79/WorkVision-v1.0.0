package ru.nabokovsg.diagnosedNK.service.measurement;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.QDiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.QElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.*;
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
    public DiagnosticEquipmentData getDiagnosticEquipmentData(Long elementId, Long partElementId) {
        QDiagnosticEquipmentData objectData = QDiagnosticEquipmentData.diagnosticEquipmentData;
        QElementData elementData = QElementData.elementData;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(elementData.elementId.eq(elementId));
        if (partElementId != null) {
            builder.and(elementData.partElementId.eq(partElementId));
        }
        return new JPAQueryFactory(em).from(objectData)
                .select(objectData)
                .where(builder)
                .fetchOne();
    }

    @Override
    public Set<ReferencePoint> getAllReferencePoint(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QReferencePoint referencePoint = QReferencePoint.referencePoint;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(geodesic.equipmentId.eq(equipmentId));
        builder.and(referencePoint.geodesicMeasurements.id.eq(geodesic.id));
        return new HashSet<>(new JPAQueryFactory(em).from(referencePoint)
                .select(referencePoint)
                .where(builder)
                .fetch());
    }

    @Override
    public Set<ControlPoint> getAllControlPoint(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QControlPoint controlPoint = QControlPoint.controlPoint;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(geodesic.equipmentId.eq(equipmentId));
        builder.and(controlPoint.geodesicMeasurements.id.eq(geodesic.id));
        return new HashSet<>(new JPAQueryFactory(em).from(controlPoint)
                .select(controlPoint)
                .where(builder)
                .fetch());
    }

    @Override
    public Set<PointDifference> getAllPointDifference(Long equipmentId) {
        QEquipmentGeodesicMeasurements geodesic = QEquipmentGeodesicMeasurements.equipmentGeodesicMeasurements;
        QPointDifference pointDifference = QPointDifference.pointDifference;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(geodesic.equipmentId.eq(equipmentId));
        builder.and(pointDifference.geodesicMeasurements.id.eq(geodesic.id));
        return new HashSet<>(new JPAQueryFactory(em)
                .from(pointDifference)
                .select(pointDifference)
                .where(builder)
                .fetch());
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