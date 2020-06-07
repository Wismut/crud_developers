package repository;

import model.DeveloperSkill;

import java.util.List;

public interface DeveloperSkillRepository {
    String TABLE_NAME = "developer_skill";
    String DEVELOPERID_COLUMN_NAME = "developer_id";
    String SKILLID_COLUMN_NAME = "skill_id";

    List<DeveloperSkill> getAllByDeveloperId(Long developerId);

    List<DeveloperSkill> getAllBySkillId(Long skillId);

    boolean isDeveloperHasSkill(Long developerId, Long skillId);
}
