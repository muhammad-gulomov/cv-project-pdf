package uz.muhammadtrying.cvprojectpdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.cvprojectpdf.entities.CV;
import uz.muhammadtrying.cvprojectpdf.entities.Experience;
import uz.muhammadtrying.cvprojectpdf.interfaces.ExperienceService;
import uz.muhammadtrying.cvprojectpdf.repositories.CVRepository;
import uz.muhammadtrying.cvprojectpdf.repositories.ExperienceRepository;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final CVRepository cVRepository;

    @Override
    public Experience addExperience(Integer cvId, String companyName, String startDateExperience, String finishedDateExperience) {
        Experience experience = Experience.builder()
                .companyName(companyName)
                .startedAt(startDateExperience)
                .finishedAt(finishedDateExperience)
                .build();
        Experience saved = experienceRepository.save(experience);
        CV cv = cVRepository.findById(cvId).get();
        cv.getExperiences().add(experience);
        cVRepository.save(cv);
        return saved;
    }
}
