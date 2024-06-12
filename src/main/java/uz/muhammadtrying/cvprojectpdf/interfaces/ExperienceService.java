package uz.muhammadtrying.cvprojectpdf.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.cvprojectpdf.entities.Experience;

@Service
public interface ExperienceService {
    Experience addExperience(Integer cvId, String companyName, String startDateExperience, String finishedDateExperience);
}
