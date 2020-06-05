package view.impl;


import command.Command;
import controller.SpecialtyController;
import model.Specialty;
import repository.GenericRepository;
import view.SpecialityView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpecialityViewImpl implements SpecialityView {
    private final SpecialtyController specialtyController;

    public SpecialityViewImpl(SpecialtyController specialtyController) {
        this.specialtyController = specialtyController;
    }

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type specialty id");
            id = MainView.getReader().readLine();
            specialtyController.deleteById(Long.parseLong(id));
            System.out.println("Specialty with id = " + id + " was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Specialty with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        try {
            System.out.println("Type name");
            String name = MainView.getReader().readLine();
            System.out.println("Type description");
            String description = MainView.getReader().readLine();
            Specialty specialty = specialtyController.save(new Specialty(name,
                    description));
            System.out.println("New specialty with id = " + specialty.getId() + " was saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New specialty wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new name");
            String name = MainView.getReader().readLine();
            System.out.println("Type new description");
            String description = MainView.getReader().readLine();
            specialtyController.update(new Specialty(Long.parseLong(id),
                    name,
                    description));
            System.out.println("Specialty with id = " + id + " was updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Specialty with id = " + id + " wasn't updated");
        }
    }

    private void getOneAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            Optional<Specialty> user = specialtyController.getById(Long.parseLong(id));
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Specialty with id = " + id + " wasn't found");
        }
    }

    private void getAllAndPrint() {
        List<Specialty> users = specialtyController.getAll();
        if (users.isEmpty()) {
            System.out.println("Specialty list is empty");
        } else {
            System.out.println("Specialties:");
            users.forEach(System.out::println);
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
