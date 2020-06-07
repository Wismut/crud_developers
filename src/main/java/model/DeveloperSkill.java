package model;

public class DeveloperSkill {
    private Long developerId;
    private Long skillId;

    public DeveloperSkill(Long developerId, Long skillId) {
        this.developerId = developerId;
        this.skillId = skillId;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
