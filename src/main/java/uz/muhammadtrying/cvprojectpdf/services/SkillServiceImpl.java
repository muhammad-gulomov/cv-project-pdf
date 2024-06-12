package uz.muhammadtrying.cvprojectpdf.services;

import com.itextpdf.io.exceptions.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.muhammadtrying.cvprojectpdf.entities.Skill;
import uz.muhammadtrying.cvprojectpdf.interfaces.SkillService;
import uz.muhammadtrying.cvprojectpdf.repositories.SkillRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public List<Skill> findAllByIds(List<Integer> skillsId) {
        return skillRepository.findAllByIdIsIn(skillsId);
    }

    @Override
    public List<Skill> addData(MultipartFile skillsFile) {
        List<Skill> skills = new ArrayList<>();
        try (InputStream inputStream = skillsFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String skillName = cell.getStringCellValue();
                        Skill skill = new Skill();
                        skill.setName(skillName);
                        skills.add(skill);
                    }
                }
            }
        } catch (IOException | java.io.IOException e) {
            e.printStackTrace();
        }
        return skillRepository.saveAll(skills);
    }
}
