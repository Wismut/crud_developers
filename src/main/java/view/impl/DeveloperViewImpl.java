package view.impl;


import command.Command;
import controller.DeveloperController;
import controller.SkillController;
import controller.SpecialtyController;
import model.Developer;
import model.Specialty;
import view.DeveloperView;
import view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
            System.out.println("Type firstName");
            firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type skill ids");
            List<Long> skillIds = Arrays.stream(MainView.getReader().readLine().split(DELIMITER))
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .collect(Collectors.toList());
            System.out.println("Type specialty id");
            Long specialtyId = Long.parseLong(MainView.getReader().readLine());
            new Developer(firstName, lastName, new Specialty());
            System.out.println("New developer with firstName = '" + firstName + "' and id = " + savedPost.getId() + " was successfully saved");
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
            System.out.println("Type new specialty id");
            String specialty = MainView.getReader().readLine();
            System.out.println("Type new skill ids");
            Developer developer = new Developer(Long.parseLong(id),
                    firstName,
                    lastName,
                    );
            developerController.update(developer);
            System.out.println("Developer with id = " + id + " was successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Developer with id = " + id + " wasn't updated");
        }
    }

    private void getOneByIdAndPrint() {
        String id = null;
        try {
            System.out.println("Type post id");
            id = MainView.getReader().readLine();
            Optional<Post> post = developerController.getById(Long.parseLong(id));
            if (post.isPresent()) {
                System.out.println(post);
            } else {
                System.out.println("Post with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't found");
        }
    }

    private void getAllByContentAndPrint() {
        String contentPart = null;
        try {
            System.out.println("Type content");
            contentPart = MainView.getReader().readLine();
            List<Post> post = developerController.getAllByContentPart(contentPart);
            if (!post.isEmpty()) {
                System.out.println(post);
            } else {
                System.out.println("Posts with content '" + contentPart + "' wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Posts with content '" + contentPart + "' wasn't found");
        }
    }

    void getAllAndPrint() {
        List<Post> posts = developerController.getAll();
        if (posts.isEmpty()) {
            System.out.println("Posts list is empty");
        } else {
            System.out.println("Posts:");
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
