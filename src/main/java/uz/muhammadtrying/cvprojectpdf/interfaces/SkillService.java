package uz.muhammadtrying.cvprojectpdf.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.muhammadtrying.cvprojectpdf.entities.Skill;

import java.util.List;

@Service
public interface SkillService {
    List<Skill> findAll();

    List<Skill> findAllByIds(List<Integer> skillsId);

    List<Skill> addData(MultipartFile skills);
}
