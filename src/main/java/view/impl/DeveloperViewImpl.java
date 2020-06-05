package view.impl;


import command.Command;
import controller.DeveloperController;
import view.DeveloperView;
import view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DeveloperViewImpl implements DeveloperView {
    private final DeveloperController developerController;

    public DeveloperViewImpl(DeveloperController developerController) {
        this.developerController = developerController;
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
            System.out.println("New post with firstName = '" + firstName + "' and id = " + savedPost.getId() + " was successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New post with firstName = '" + firstName + "' wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new content");
            String content = MainView.getReader().readLine();
            System.out.println("Type new created date and time in the format " + LOCALDATETIME_PATTERN);
            String created = MainView.getReader().readLine();
            System.out.println("Type new updated date and time in the format " + LOCALDATETIME_PATTERN);
            String updated = MainView.getReader().readLine();
            Post post = new Post(Long.parseLong(id),
                    content,
                    LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                    LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            developerController.update(post);
            System.out.println("Post with id = " + id + " was successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't updated");
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
