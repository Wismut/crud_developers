package ua.wismut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.wismut.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> getByName(String name);

    List<Skill> getAllByNames(List<String> names);

    List<Long> saveBatch(List<Skill> skills);
}
