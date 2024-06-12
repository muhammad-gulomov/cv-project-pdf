package uz.muhammadtrying.cvprojectpdf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.cvprojectpdf.entities.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
}