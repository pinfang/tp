package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidClearBudgetException;
import seedu.duke.exceptions.InvalidClearRepaymentException;
import seedu.duke.exceptions.InvalidClearSpendingException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command for purging all data stored in the application.
 */
//@@author xieyaoyue
public class PurgeDataCommand extends Command {
    private static Logger logger = Logger.getLogger("PurgeDataCommand");

    /**
     * Purges all data stored in the application.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     * @throws IOException if the application fails to purge all data
     */
    public void execute(Data data, Ui ui) throws IOException {
        logger.log(Level.FINE, "going to purge data");
        ClearSpendingListCommand clearSpendingListCommand = new ClearSpendingListCommand(true, 0);
        ClearRepaymentListCommand clearRepaymentListCommand = new ClearRepaymentListCommand(true, 0);
        ClearBudgetCommand clearBudgetCommand = new ClearBudgetCommand();
        try {
            clearSpendingListCommand.execute(data, ui);
        } catch (InvalidClearSpendingException | InvalidNumberException ignore) {
            //this is expected
        }
        try {
            clearRepaymentListCommand.execute(data, ui);
        } catch (InvalidClearRepaymentException | InvalidNumberException ignore) {
            //this is expected
        }
        try {
            clearBudgetCommand.execute(data, ui);
        } catch (InvalidClearBudgetException ignore) {
            //this is expected
        }
        ui.printPurgeData();
    }
}
