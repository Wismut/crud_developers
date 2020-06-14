package view;


import command.Command;
import view.impl.MainView;

import java.io.IOException;

public interface View {
    String DEVELOPER_NUMBER = "1";
    String SKILL_NUMBER = "2";
    String SPECIALTY_NUMBER = "3";
    String SAVE_COMMAND_LETTER = "s";
    String GET_BY_ID_COMMAND_LETTER = "g";
    String GET_BY_NAME_COMMAND_LETTER = "n";
    String DELETE_COMMAND_LETTER = "d";
    String UPDATE_COMMAND_LETTER = "u";
    String GET_ALL_COMMAND_LETTER = "a";
    String GET_BY_SPECIALTY = "c";

    default Command getCommand() {
        Command command;
        try {
            String type;
            do {
                System.out.println("Type " + DELETE_COMMAND_LETTER + " if you want to delete record from the database");
                System.out.println("Type " + UPDATE_COMMAND_LETTER + " if you want to update record in the database");
                System.out.println("Type " + SAVE_COMMAND_LETTER + " if you want to save record to the database");
                System.out.println("Type " + GET_BY_ID_COMMAND_LETTER + " if you want to get one record by id from the database");
                System.out.println("Type " + GET_ALL_COMMAND_LETTER + " if you want to get all records from the database");
                printActionsInfo();
                System.out.println("Type q for quit");
                type = MainView.getReader().readLine();
                if ("q".equals(type)) {
                    System.exit(0);
                }
                command = Command.getCommandByLetter(type);
            } while (command == null);
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can't get command");
    }

    void execute(Command command);

    void printActionsInfo();
}
