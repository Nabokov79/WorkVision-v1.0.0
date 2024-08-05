package ru.nabokovsg.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nabokovsg.company.model.Department;

import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select b.departments from Branch b where b.id=?1")
    Set<Department> findByBranch(Long branchId);

    boolean existsByFullName(String fullName);
}