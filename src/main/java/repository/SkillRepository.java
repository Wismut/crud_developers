package repository;

import model.Skill;

import java.util.Optional;

public interface SkillRepository extends GenericRepository<Skill, Long> {
    String TABLE_NAME = "skills";
    String ID_COLUMN_NAME = "id";
    String NAME_COLUMN_NAME = "name";

    Optional<Skill> getByName(String name);
}
