package ru.nabokovsg.diagnosedNK.mapper.norms;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.AcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;

@Mapper(componentModel = "spring")
public interface AcceptableDeviationsGeodesyMapper {

    AcceptableDeviationsGeodesy mapToPermissibleDeviationsGeodesy(AcceptableDeviationsGeodesyDto geodesyDto);

    ResponseAcceptableDeviationsGeodesyDto mapToResponseAcceptableDeviationsGeodesyDto(
                                                                         AcceptableDeviationsGeodesy geodesy);
}