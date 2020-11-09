package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidClearBudgetException;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command for clearing the budget previously set by the user.
 */
//@@author xieyaoyue
public class ClearBudgetCommand extends Command {
    private static Logger logger = Logger.getLogger("ClearBudgetCommand");

    /**
     * Clears the budget previously set by the user.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     * @throws InvalidClearBudgetException if the budget data is already empty
     * @throws IOException if the application fails to clear the budget
     */
    @Override
    public void execute(Data data, Ui ui) throws
        InvalidClearBudgetException, IOException {
        if (!data.budget.hasBudget) {
            throw new InvalidClearBudgetException();
        }
        logger.log(Level.FINE, "going to clear budget");
        data.budget.clearBudget();
        ui.printClearBudget();
    }
}
