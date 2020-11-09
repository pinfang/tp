package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.ReminderCommand;
import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidCommandException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.text.ParseException;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    private static Ui ui;
    private static ReminderCommand reminderCommand;
    private static Data data;

    /**
     * Runs the program until termination.
     */
    private static void run() {
        ui.printWelcomeMessage();
        reminderCommand.execute(data, ui);
        boolean isExit = false;
        do {
            try {
                String fullCommand = ui.getUserInput();
                Command c = Parser.parseCommand(fullCommand);
                c.execute(data, ui);
                isExit = c.isExit();
            } catch (ParseException e) {
                ui.printErrorMessage(new InvalidCommandException().getMessage());
            } catch (NumberFormatException e) {
                ui.printErrorMessage(new InvalidNumberException().getMessage());
            } catch (Exception e) {
                ui.printErrorMessage(e.getMessage());
            }
        } while (!isExit);
        ui.printGoodbyeMessage();
    }

    /**
     * Sets up the required objects and loads data from the storage file.
     */
    public Duke() {
        ui = new Ui();
        reminderCommand = new ReminderCommand();
        try {
            data = new Data();
        } catch (Exception e) {
            ui.printErrorMessage(e.toString());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
