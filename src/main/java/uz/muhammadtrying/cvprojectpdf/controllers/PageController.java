package uz.muhammadtrying.cvprojectpdf.controllers;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.muhammadtrying.cvprojectpdf.entities.CV;
import uz.muhammadtrying.cvprojectpdf.entities.Education;
import uz.muhammadtrying.cvprojectpdf.entities.Experience;
import uz.muhammadtrying.cvprojectpdf.entities.Skill;
import uz.muhammadtrying.cvprojectpdf.interfaces.CVservice;
import uz.muhammadtrying.cvprojectpdf.interfaces.EducationService;
import uz.muhammadtrying.cvprojectpdf.interfaces.ExperienceService;
import uz.muhammadtrying.cvprojectpdf.interfaces.SkillService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {
    private final SkillService skillService;
    private final HttpSession httpSession;
    @Qualifier("CVservice")
    private final CVservice vservice;
    private final EducationService educationService;
    private final ExperienceService experienceService;

    @GetMapping
    public String index(Model model) {
        if (httpSession.getAttribute("cv") != null) {
            CV cv = (CV) httpSession.getAttribute("cv");
            model.addAttribute("cv", cv);
        }
        if (httpSession.getAttribute("educations") != null) {
            List<Education> educations = (List<Education>) httpSession.getAttribute("educations");
            model.addAttribute("educations", educations);
        }
        if (httpSession.getAttribute("experiences") != null) {
            List<Experience> experiences = (List<Experience>) httpSession.getAttribute("experiences");
            model.addAttribute("experiences", experiences);
        }
        if (httpSession.getAttribute("skills") != null) {
            List<Skill> skills = (List<Skill>) httpSession.getAttribute("skills");
            model.addAttribute("skills", skills);
        }
        return "index";
    }

    @PostMapping("/save/photo")
    public String savePhoto(@RequestParam MultipartFile file, Model model) {
        CV cv = vservice.addCV(file);
        httpSession.setAttribute("cvId", cv.getId());
        model.addAttribute("cv", cv);
        return "redirect:/";
    }

    @GetMapping("/user/photo")
    public void getPhoto(HttpServletResponse response) throws IOException {
        if (httpSession.getAttribute("cvId") != null) {
            Integer cvId = (Integer) httpSession.getAttribute("cvId");
            CV cv = vservice.findById(cvId);
            response.setContentType("image/jpeg");
            response.getOutputStream().write(cv.getPhoto());
            response.getOutputStream().close();
        }
    }

    @PostMapping("/user/basic/info")
    public String generateCv(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String fathersName,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) List<Integer> skillsId
    ) {
        Integer cvId = (Integer) httpSession.getAttribute("cvId");
        CV cvSaved = vservice.findById(cvId);
        CV cv = vservice.addData(cvSaved, firstName, lastName, fathersName, age, skillsId, email, phone);
        httpSession.setAttribute("cvId", cvId);
        httpSession.setAttribute("cv", cv);
        return "redirect:/";
    }

    @PostMapping("/add/education")
    public String addEducation(
            @RequestParam String studyPlace,
            @RequestParam String startDateEducation,
            @RequestParam String finishedDateEducation
    ) {
        List<Education> educations = new ArrayList<>();
        if (httpSession.getAttribute("educations") != null)
            educations = new ArrayList<>((List<Education>) httpSession.getAttribute("educations"));
        Integer cvId = (Integer) httpSession.getAttribute("cvId");
        educations.add(educationService.addEducation(cvId, studyPlace, startDateEducation, finishedDateEducation));
        httpSession.setAttribute("educations", educations);
        return "redirect:/";
    }

    @GetMapping("/remove/education/{educationId}")
    public String removeEducation(@PathVariable Integer educationId) {
        List<Education> educations = new ArrayList<>();
        if (httpSession.getAttribute("educations") != null)
            educations = new ArrayList<>((List<Education>) httpSession.getAttribute("educations"));
        List<Education> list = educations.stream().filter(education -> !education.getId().equals(educationId)).toList();
        httpSession.setAttribute("educations", list);
        return "redirect:/";
    }

    @PostMapping("/add/experience")
    public String addExperience(
            @RequestParam String companyName,
            @RequestParam String startDateExperience,
            @RequestParam String finishedDateExperience
    ) {
        List<Experience> experiences = new ArrayList<>();
        if (httpSession.getAttribute("experiences") != null) {
            experiences = new ArrayList<>((List<Experience>) httpSession.getAttribute("experiences"));
        }
        Integer cvId = (Integer) httpSession.getAttribute("cvId");
        Experience experience = experienceService.addExperience(cvId, companyName, startDateExperience, finishedDateExperience);
        experiences.add(experience);
        httpSession.setAttribute("experiences", experiences);
        return "redirect:/";
    }

    @GetMapping("/remove/experience/{experienceId}")
    public String removeExperience(@PathVariable Integer experienceId) {
        List<Experience> experiences = new ArrayList<>();
        if (httpSession.getAttribute("experiences") != null) {
            experiences = new ArrayList<>((List<Experience>) httpSession.getAttribute("experiences"));
        }
        List<Experience> list = experiences.stream().filter(experience -> !experience.getId().equals(experienceId)).toList();
        httpSession.setAttribute("experiences", list);
        return "redirect:/";
    }

    @PostMapping("/generate/cv")
    public void generateCV(HttpServletResponse response) {
        try {
            Integer cvId = (Integer) httpSession.getAttribute("cvId");
            CV cv = vservice.findById(cvId);

            String htmlContent = vservice.generateCV(cv);
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(htmlContent, pdfStream);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cv.pdf");
            response.setContentLength(pdfStream.size());
            response.getOutputStream().write(pdfStream.toByteArray());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload/skills")
    public String uploadSkills(@RequestParam MultipartFile skills) {
        List<Skill> skillsSaved = skillService.addData(skills);
        httpSession.setAttribute("skills", skillsSaved);
        return "redirect:/";
    }
}
