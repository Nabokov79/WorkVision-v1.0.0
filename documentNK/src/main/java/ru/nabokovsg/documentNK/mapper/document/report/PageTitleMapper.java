package ru.nabokovsg.documentNK.mapper.document.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.documentCreate.LaboratoryEmployeeDto;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;

@Mapper(componentModel = "spring")
public interface PageTitleMapper {

    @Mapping(target = "id", ignore = true)
    PageTitle mapToPageTitle(PageTitleTemplate pageTitleTemplate
                           , LaboratoryEmployeeDto chief
                           , String numberAndDate
                           , String year
                           , String address);


    PageTitle mapToUpdatePageTitle(PageTitle pageTitle
                                 , LaboratoryEmployeeDto chief
                                 , String numberAndDate
                                 , String address);
}