package uz.muhammadtrying.cvprojectpdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.cvprojectpdf.entities.CV;
import uz.muhammadtrying.cvprojectpdf.entities.Education;
import uz.muhammadtrying.cvprojectpdf.interfaces.EducationService;
import uz.muhammadtrying.cvprojectpdf.repositories.CVRepository;
import uz.muhammadtrying.cvprojectpdf.repositories.EducationRepository;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final CVRepository cVRepository;

    @Override
    public Education addEducation(Integer cvId, String studyPlace, String startDateEducation, String finishedDateEducation) {
        Education education = Education.builder()
                .placeOfStudy(studyPlace)
                .startedAt(startDateEducation)
                .finishedAt(finishedDateEducation)
                .build();
        Education savedEducation = educationRepository.save(education);
        CV cv = cVRepository.findById(cvId).get();
        cv.getEducations().add(savedEducation);
        cVRepository.save(cv);
        return savedEducation;
    }
}
