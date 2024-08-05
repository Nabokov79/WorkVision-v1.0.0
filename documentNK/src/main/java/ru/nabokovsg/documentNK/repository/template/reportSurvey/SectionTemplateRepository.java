package ru.nabokovsg.documentNK.repository.template.reportSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

import java.util.Set;

public interface SectionTemplateRepository extends JpaRepository<SectionTemplate, Long> {

    @Query("select t.sectionsTemplate from ReportTemplate t where t.id=?1")
    Set<SectionTemplate> findByReportTemplateId(Long id);

    boolean existsByReportTemplateIdAndSectionName(Long reportTemplateId, String sectionName);
}