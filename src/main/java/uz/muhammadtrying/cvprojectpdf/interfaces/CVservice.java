package uz.muhammadtrying.cvprojectpdf.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.muhammadtrying.cvprojectpdf.entities.CV;

import java.util.List;

@Service
public interface CVservice {

    CV addCV(MultipartFile file);

    CV addData(CV cv, String firstName, String lastName, String fathersName, Integer age, List<Integer> skillsId, String email, String phone);

    CV findById(Integer cvId);

    String generateCV(CV cv);
}
