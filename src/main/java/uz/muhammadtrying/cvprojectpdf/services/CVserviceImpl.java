package uz.muhammadtrying.cvprojectpdf.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import uz.muhammadtrying.cvprojectpdf.entities.CV;
import uz.muhammadtrying.cvprojectpdf.entities.Skill;
import uz.muhammadtrying.cvprojectpdf.interfaces.CVservice;
import uz.muhammadtrying.cvprojectpdf.interfaces.SkillService;
import uz.muhammadtrying.cvprojectpdf.repositories.CVRepository;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CVserviceImpl implements CVservice {

    private final CVRepository cVRepository;
    private final SkillService skillService;
    private final TemplateEngine templateEngine;

    @SneakyThrows
    @Override
    public CV addCV(MultipartFile file) {
        CV cv = CV.builder()
                .photo(file.getBytes())
                .build();
        return cVRepository.save(cv);
    }

    @Override
    public CV addData(CV cv, String firstName, String lastName, String fathersName, Integer age, List<Integer> skillsId, String email, String phone) {
        cv.setFirstName(firstName);
        cv.setLastName(lastName);
        cv.setFathersName(fathersName);
        cv.setAge(age);
        cv.setEmail(email);
        cv.setPhone(phone);
        List<Skill> skills = skillService.findAllByIds(skillsId);
        cv.setSkills(skills);
        return cVRepository.save(cv);
    }

    @Override
    public CV findById(Integer cvId) {
        return cVRepository.findById(cvId).get();
    }

    @Override
    public String generateCV(CV cv) {
        Context context = new Context();
        byte[] photoBytes = cv.getPhoto();
        String encodedPhoto = Base64.getEncoder().encodeToString(photoBytes);
        context.setVariable("cv", cv);
        context.setVariable("photo", encodedPhoto);
        return templateEngine.process("cv", context);
    }
}
