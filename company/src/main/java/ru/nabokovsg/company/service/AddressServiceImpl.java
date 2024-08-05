package ru.nabokovsg.company.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.address.AddressDto;
import ru.nabokovsg.company.dto.address.ResponseAddressDto;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.AddressMapper;
import ru.nabokovsg.company.model.Address;
import ru.nabokovsg.company.model.QAddress;
import ru.nabokovsg.company.repository.AddressRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseAddressDto save(AddressDto addressDto) {
        return mapper.mapToFullAddressDto(
                Objects.requireNonNullElseGet(getByPredicate(addressDto)
                                            , () -> repository.save(mapper.mapToAddress(addressDto)))
        );
    }

    @Override
    public ResponseAddressDto update(AddressDto addressDto) {
        if (repository.existsById(addressDto.getId())) {
            return mapper.mapToFullAddressDto(repository.save(mapper.mapToAddress(addressDto)));
        }
        throw new NotFoundException(String.format("Address with id=%s not found for update.", addressDto.getId()));
    }

    @Override
    public Address get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Address with id=%s not found", id)));
    }

    @Override
    public List<ResponseAddressDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToFullAddressDto)
                         .toList();
    }

    @Override
    public String delete(Long id) {
        Address address = repository.findById(id).orElseThrow(() -> new NotFoundException(
                        String.format("Address with id=%s not found for delete.", id)));
        repository.deleteById(id);
        return String.join(" ", address.getCity(),
                                                 address.getStreet(),
                                                 address.getHouseNumber());
    }

    private Address getByPredicate(AddressDto addressDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (addressDto.getIndex() != null) {
            booleanBuilder.and(QAddress.address.index.eq(addressDto.getIndex()));
        }
        if (addressDto.getCity() != null) {
            booleanBuilder.and(QAddress.address.city.eq(addressDto.getCity()));
        }
        if (addressDto.getStreet() != null) {
            booleanBuilder.and(QAddress.address.street.eq(addressDto.getStreet()));
        }
        if (addressDto.getHouseNumber() != null) {
            booleanBuilder.and(QAddress.address.houseNumber.eq(addressDto.getHouseNumber()));
        }
        if (addressDto.getBuildingNumber() != null) {
            booleanBuilder.and(QAddress.address.buildingNumber.eq(addressDto.getBuildingNumber()));
        }
        if (addressDto.getLetter() != null) {
            booleanBuilder.and(QAddress.address.letter.eq(addressDto.getLetter()));
        }
        QAddress address = QAddress.address;
        return new JPAQueryFactory(em).from(address)
                                      .select(address)
                                      .where(booleanBuilder)
                                      .fetchOne();
    }
}