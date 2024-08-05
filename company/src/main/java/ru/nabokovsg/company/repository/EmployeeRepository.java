package ru.nabokovsg.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.company.model.Employee;
import ru.nabokovsg.company.model.enums.DivisionType;

import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByNameAndPatronymicAndSurname(String name, String patronymic, String post);

    Set<Employee> findAllByPlaceWorkIdAndDivisionType(Long id, DivisionType divisionType);
}