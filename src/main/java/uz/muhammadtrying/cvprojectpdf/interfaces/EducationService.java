package uz.muhammadtrying.cvprojectpdf.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.cvprojectpdf.entities.Education;

import java.util.Date;

@Service
public interface EducationService {
    Education addEducation(Integer cvId, String studyPlace, String startDateEducation, String finishedDateEducation);
}
