package ru.nabokovsg.documentNK.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.*;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.DivisionDataDto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StringBuilderServiceImpl implements StringBuilderService {

    private final ConstantMonthService monthService;

    @Override
    public String buildMeasuringTool(MeasuringToolDto measuringTool) {
        return String.join(" ", measuringTool.getToll()
                , "«", measuringTool.getModel(), "»"
                , "зав. №", measuringTool.getWorkNumber()
                , "свидетельство о поверке №", measuringTool.getCertificateNumber());
    }

    @Override
    public String buildDocumentation(DocumentationDto documentation) {
        String string = String.join("", "«", documentation.getTitle(), "»");
        if (documentation.getNumber() != null) {
            string = String.join(" ", documentation.getNumber(), string);
        }
        if (documentation.getView() != null) {
            string = String.join(" ", documentation.getView(), string);
        }
        return string;
    }

    @Override
    public String buildDivision(DivisionDataDto param
                              , DivisionDto division
                              , List<LaboratoryCertificateDto> certificates) {
        String divisionData = String.join(". "
                , getDivisionName(division.getFullName(), param.getUserDivisionName()));
        if (param.getSpecifyAddress()) {
            divisionData = String.join(". ", divisionData, buildAddress(division.getAddress()));
        }
        if (param.getSpecifyCertificate()) {
            divisionData = String.join(". ", divisionData, buildCertificate(certificates));
        }
        return divisionData;
    }

    @Override
    public String buildCertificate(List<LaboratoryCertificateDto> certificates) {
        LaboratoryCertificateDto certificate = certificates.stream()
                .filter(l -> l.getEndDate().isAfter(LocalDate.now()))
                .toList()
                .get(0);
        if (certificate == null) {
            certificate = certificates.stream()
                    .max(Comparator.comparing(LaboratoryCertificateDto::getEndDate))
                    .orElseThrow(NoSuchElementException::new);
        }
        return String.join(" ", certificate.getDocumentType(),
                "№",
                certificate.getLicenseNumber(),
                "от",
                String.join(".", String.valueOf(certificate.getStartDate().getDayOfMonth())
                                        , monthService.getNumber(certificate.getStartDate())
                                        , String.valueOf(certificate.getStartDate().getYear())));
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

    @Override
    public String buildEmployeeContacts(EmployeeDto employeeDto) {
        return String.join(" "
                , String.join(" ","тел./факс", employeeDto.getPhone(),"/", employeeDto.getFax())
                , String.join(" ", "E-mail:", employeeDto.getEmail()));
    }

    @Override
    public String numberAndDate(LocalDate date, Integer documentNumber) {
        return String.join(" "
                 , "№", String.valueOf(documentNumber)
                         , "от", String.valueOf(date.getDayOfMonth())
                         , monthService.getName(date)
                         , String.valueOf(date.getYear()), "г.");
    }

    @Override
    public String buildInstallationLocation(String installationLocation, String building, String buildingType) {
        String[] workPlace = building.split(",")[0].split(" ");
        if (workPlace[0].equals(buildingType)) {
            workPlace[0] = "котельной";
        }
        return String.join(" ", installationLocation, String.join(" ", workPlace));
    }

    @Override
    public String buildResultDocumentCreate(DocumentCreationDataDto documentCreationDataDto) {
        return String.join(" "
                , documentCreationDataDto.getDiagnosticDocumentType().getTitle()
                , "№", String.valueOf(documentCreationDataDto.getDocumentNumber())
                , "от", String.valueOf(documentCreationDataDto.getDate()));
    }

    @Override
    public String buildProtocolTitle(String titleTemplate, LocalDate date, Integer documentNumber) {
        return String.join(" ", titleTemplate, numberAndDate(date, documentNumber));
    }

    private String getDivisionName(String name, String userDivisionName){
        if (userDivisionName != null) {
            return userDivisionName;
        }
        return name;
    }
}
