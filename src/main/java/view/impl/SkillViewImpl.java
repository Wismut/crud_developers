package view.impl;


import command.Command;
import controller.SkillController;
import model.Skill;
import view.SkillView;

import java.util.List;
import java.util.Optional;

public class SkillViewImpl implements SkillView {
    private final SkillController skillController;

    public SkillViewImpl(SkillController skillController) {
        this.skillController = skillController;
    }

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type skill id");
            id = MainView.getReader().readLine();
            skillController.deleteById(Long.parseLong(id));
            System.out.println("Skill with id = " + id + " was successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Skill with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        String name = null;
        try {
            System.out.println("Type name");
            name = MainView.getReader().readLine();
            Skill skill = skillController.save(new Skill(name));
            System.out.println("New skill with name " + skill.getName() + " id = " + skill.getId() + " was successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New skill with name = " + name + " wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new name");
            String name = MainView.getReader().readLine();
            skillController.update(new Skill(Long.parseLong(id),
                    name));
            System.out.println("Skill with id = " + id + " was updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Skill with id = " + id + " wasn't updated");
        }
    }

    private void getOneAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            Optional<Skill> region = skillController.getById(Long.parseLong(id));
            if (region.isPresent()) {
                System.out.println(region);
            } else {
                System.out.println("Skill with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Skill with id = " + id + " wasn't found");
        }
    }

    private void getAllAndPrint() {
        List<Skill> regions = skillController.getAll();
        if (regions.isEmpty()) {
            System.out.println("Skills list is empty");
        } else {
            System.out.println("Skills:");
            regions.forEach(System.out::println);
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
                getOneAndPrint();
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

    }
}
