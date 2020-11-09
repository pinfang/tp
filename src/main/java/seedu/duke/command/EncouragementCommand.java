package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

/**
 * Represents the command for giving the user encouragements.
 */
//@@author xieyaoyue
public class EncouragementCommand extends Command {

    /**
     * Prints out an encouragement quote.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     */
    @Override
    public void execute(Data data, Ui ui) {
        ui.printEncouragementMessage();
    }
}
