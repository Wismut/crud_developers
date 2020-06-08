package service;

import model.DeveloperSkill;
import repository.DeveloperSkillRepository;

public class DeveloperSkillService {
    private final DeveloperSkillRepository developerSkillRepository;

    public DeveloperSkillService(DeveloperSkillRepository developerSkillRepository) {
        this.developerSkillRepository = developerSkillRepository;
    }

    public DeveloperSkill save(DeveloperSkill developerSkill) {
        return developerSkillRepository.save(developerSkill);
    }
}
