package ru.nabokovsg.laboratoryNK.service;

import org.springframework.stereotype.Component;
import ru.nabokovsg.laboratoryNK.dto.client.AddressDto;
import ru.nabokovsg.laboratoryNK.dto.client.BuildingDto;
import ru.nabokovsg.laboratoryNK.dto.client.EmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.client.EquipmentDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;

@Component
public class StringBuilderServiceImpl implements StringBuilderService {

    @Override
    public String buildInitials(EmployeeDto employee) {
        return String.join(". ", String.join(".", String.valueOf(employee.getName().charAt(0))
                                        , String.valueOf(employee.getPatronymic().charAt(0))).toUpperCase()
                                        , employee.getSurname());
    }

    @Override
    public String buildBuilding(BuildingDto building) {
        String workPlace = building.getBuildingType();
        if (building.getLogin() != null) {
            workPlace = String.join(" ", workPlace, "«", building.getLogin(), "»");
        }
        return String.join(", ", workPlace, buildAddress(building.getAddress()));
    }

    @Override
    public String buildEquipmentDiagnosed(EquipmentDto equipment) {
        String equipmentDiagnosed = equipment.getEquipmentName();
        if (equipment.getModel() != null) {
            equipmentDiagnosed = String.join(" ", equipmentDiagnosed, equipment.getModel());
        }
        if (equipment.getStationaryNumber() != null) {
            equipmentDiagnosed = String.join("", equipmentDiagnosed, ",");
            equipmentDiagnosed = String.join(" ", equipmentDiagnosed
                                                        , "ст. №"
                                                        , String.valueOf(equipment.getStationaryNumber()));
        }
        if (equipment.getVolume() != null) {
            equipmentDiagnosed = String.join(" ", equipmentDiagnosed
                                                        , "V = "
                                                        , String.valueOf(equipment.getVolume())
                                                        , "м3");
        }
        return equipmentDiagnosed;
    }

    @Override
    public String buildDiagnosticDocumentType(DiagnosticDocumentType diagnosticDocumentType) {
        return String.join(", ", diagnosticDocumentType.getTitle(), diagnosticDocumentType.getSubtitle());
    }

    @Override
    public String buildAddress(AddressDto address) {
        String string = String.join(", ", address.getCity()
                , String.join(" ", address.getStreet()
                        , "д.", String.valueOf(address.getHouseNumber())));
        if (address.getBuildingNumber() != null) {
            string = String.join(", ", string, String.join(""
                    , "корп.", String.valueOf(address.getBuildingNumber())));
        }
        if (address.getLetter() != null) {
            string = String.join(", ", string, String.join("", "лит.", address.getLetter()));
        }
        if (address.getIndex() != null) {
            return String.join(", ", String.valueOf(address.getIndex()), string);
        } else {
            return string;
        }
    }
}