package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

//@@author killingbear999
/** It is to exit the program. */
public class ExitCommand extends Command {

    public ExitCommand() {
    }

    @Override
    public void execute(Data data, Ui ui) {
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
