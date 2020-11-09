package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command for providing help for the user.
 */
//@@author xieyaoyue
public class HelpCommand extends Command {
    private static Logger logger = Logger.getLogger("HelpCommand");

    /**
     * Outputs a help window for the user.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     */
    @Override
    public void execute(Data data, Ui ui) {
        logger.log(Level.FINE, "opening up help window");
        ui.printHelp();
    }
}
