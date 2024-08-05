package ru.nabokovsg.laboratoryNK.dto.measuringTool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class SearchParameters {

    private List<Long> ids;
    private String toll;
    private String model;
    private String workNumber;
    private LocalDate manufacturing;
    private LocalDate exploitation;
    private String manufacturer;
    private String organization;
    private String controlType;
    private Long employeeId;
}