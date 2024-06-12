package uz.muhammadtrying.cvprojectpdf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.cvprojectpdf.entities.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findAllByIdIsIn(List<Integer> ids);
}