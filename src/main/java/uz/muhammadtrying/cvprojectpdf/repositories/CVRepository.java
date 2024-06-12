package uz.muhammadtrying.cvprojectpdf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.cvprojectpdf.entities.CV;

public interface CVRepository extends JpaRepository<CV, Integer> {
}