package view.impl;


import command.Command;
import controller.DeveloperController;
import controller.SkillController;
import controller.SpecialtyController;
import model.Developer;
import model.Skill;
import model.Specialty;
import view.DeveloperView;
import view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static repository.GenericRepository.DELIMITER;

public class DeveloperViewImpl implements DeveloperView {
    private final DeveloperController developerController;
    private final SkillController skillController;
    private final SpecialtyController specialtyController;

    public DeveloperViewImpl(DeveloperController developerController, SkillController skillController, SpecialtyController specialtyController) {
        this.developerController = developerController;
        this.skillController = skillController;
        this.specialtyController = specialtyController;
    }

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type developer id");
            id = MainView.getReader().readLine();
            developerController.deleteById(Long.parseLong(id));
            System.out.println("Developer with id = " + id + " was successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Developer with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        String firstName = null;
        try {
            List<Skill> allSkills = skillController.getAll();
            List<Specialty> specialties = specialtyController.getAll();
            System.out.println("Type firstName");
            firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type skill names");
            List<Skill> skills = Arrays.stream(MainView.getReader().readLine().split(DELIMITER))
                    .map(skillName -> {
                        Optional<Skill> skill = allSkills.stream()
                                .filter(s -> s.getName().equalsIgnoreCase(skillName))
                                .findFirst();
                        return skill.orElseGet(() -> skillController.save(new Skill(skillName)));
                    })
                    .collect(Collectors.toList());
            System.out.println("Type specialty name");
            String specialtyName = MainView.getReader().readLine();
            Specialty specialty;
            Optional<Specialty> specialtyOptional = specialties.stream()
                    .filter(s -> s.getName().equalsIgnoreCase(specialtyName))
                    .findAny();
            specialty = specialtyOptional.orElseGet(() -> specialtyController.save(new Specialty(specialtyName)));
            Developer developer = new Developer(firstName,
                    lastName,
                    specialty,
                    skills);
            Developer savedDeveloper = developerController.save(developer);
            System.out.println("New developer with firstName = '" + firstName + "' and id = " + savedDeveloper.getId() + " was successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New developer with firstName = '" + firstName + "' wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new firstName");
            String firstName = MainView.getReader().readLine();
            System.out.println("Type new lastName");
            String lastName = MainView.getReader().readLine();
            Developer developer = new Developer(Long.parseLong(id),
                    firstName,
                    lastName);
            Developer updatedDeveloper = developerController.update(developer);
            System.out.println("Developer with id = " + id + " was successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Developer with id = " + id + " wasn't updated");
        }
    }

    private void getOneByIdAndPrint() {
        String id = null;
        try {
            System.out.println("Type developer id");
            id = MainView.getReader().readLine();
            Optional<Developer> post = developerController.getById(Long.parseLong(id));
            if (post.isPresent()) {
                System.out.println(post);
            } else {
                System.out.println("Developer with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Developer with id = " + id + " wasn't found");
        }
    }

    private void getAllByContentAndPrint() {
        String specialityName = null;
        try {
            System.out.println("Type content");
            specialityName = MainView.getReader().readLine();
            List<Developer> post = developerController.getAllBySpeciality(specialityName);
            if (!post.isEmpty()) {
                System.out.println(post);
            } else {
                System.out.println("Developers with speciality '" + specialityName + "' wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Developers with speciality '" + specialityName + "' wasn't found");
        }
    }

    void getAllAndPrint() {
        List<Developer> posts = developerController.getAll();
        if (posts.isEmpty()) {
            System.out.println("Developer list is empty");
        } else {
            System.out.println("Developers:");
            posts.forEach(System.out::println);
        }
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                deleteAndPrint();
                return;
            case SAVE:
                saveAndPrint();
                return;
            case UPDATE:
                updateAndPrint();
                return;
            case GET_BY_ID:
                getOneByIdAndPrint();
                return;
            case GET_BY_CONTENT:
                getAllByContentAndPrint();
                return;
            case GET_ALL:
                getAllAndPrint();
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }

    @Override
    public void printActionsInfo() {
        System.out.println("Type " + View.GET_BY_CONTENT_COMMAND_LETTER + " if you want to get all records with some content from the database");
    }
}
