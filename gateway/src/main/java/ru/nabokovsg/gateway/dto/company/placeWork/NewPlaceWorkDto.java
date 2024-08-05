package ru.nabokovsg.gateway.dto.company.placeWork;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные нового места работы сотрудника")
public class NewPlaceWorkDto {

    @Schema(description = "Индентификатор организации")
    @NotNull(message = "organization id user should not be blank")
    @Positive(message = "organization id user must be positive")
    private Long organizationId;
    @Schema(description = "Индентификатор филиала")
    @NotNull(message = "branch id user should not be blank")
    @Positive(message = "branch id user must be positive")
    private Long branchId;
    @Schema(description = "Индентификатор подразделения филиала")
    private Long departmentId;
    @Schema(description = "Индентификатор эксплуатационного участка")
    private Long exploitationRegionId;
    @Schema(description = "Индентификатор котельной, ЦТП")
    private Long buildingId;
}