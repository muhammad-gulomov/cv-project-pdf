package uz.muhammadtrying.cvprojectpdf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.cvprojectpdf.entities.Education;

public interface EducationRepository extends JpaRepository<Education, Integer> {
}