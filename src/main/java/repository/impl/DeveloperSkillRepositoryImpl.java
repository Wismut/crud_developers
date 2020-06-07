package repository.impl;

import model.DeveloperSkill;
import repository.DeveloperSkillRepository;

import java.util.List;

public class DeveloperSkillRepositoryImpl implements DeveloperSkillRepository {
    public List<DeveloperSkill> getAllByDeveloperId(Long developerId) {
        return null;
    }

    public List<DeveloperSkill> getAllBySkillId(Long skillId) {
        return null;
    }

    public boolean isDeveloperHasSkill(Long developerId, Long skillId) {
        return false;
    }
}
