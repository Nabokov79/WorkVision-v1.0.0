package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;

import java.util.List;
import java.util.Set;

public interface LaboratoryEmployeeRepository extends JpaRepository<LaboratoryEmployee, Long> {

    @Query("select e.employeeId from LaboratoryEmployee e where e.employeeId in :ids")
    Set<Long> findAllByEmployeeId(@Param("ids") List<Long> ids);
}