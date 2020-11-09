package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidClearSpendingException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command for clearing the spending list.
 */
//@@author xieyaoyue
public class ClearSpendingListCommand extends Command {
    private boolean isClearAll;
    private int clearIndex;
    private static Logger logger = Logger.getLogger("ClearSpendingListCommand");

    public ClearSpendingListCommand(boolean isClearAll, int clearIndex) {
        this.isClearAll = isClearAll;
        this.clearIndex = clearIndex;
    }

    /**
     * Clears the spending list by either clearing a single entry or the whole list.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     * @throws IOException if the application fails to clear the spending list
     * @throws InvalidClearSpendingException if the spending list is already empty
     * @throws InvalidNumberException if the user inputs an invalid index of the spending list
     */
    public void execute(Data data, Ui ui) throws IOException,
            InvalidClearSpendingException, InvalidNumberException {
        int size = data.spendingList.getListSize();
        if (size == 0) {
            throw new InvalidClearSpendingException();
        }
        if (clearIndex > size) {
            logger.log(Level.FINE, "clearIndex is invalid");
            throw new InvalidNumberException();
        }
        logger.log(Level.FINE, "going to clear spending list");
        if (!isClearAll) {
            int beforeClearSize = data.spendingList.getListSize();
            ui.printClearIndex(data.spendingList.getItem(clearIndex - 1));
            data.spendingList.deleteItem(clearIndex - 1);
            int afterClearSize = data.spendingList.getListSize();
            assert beforeClearSize - afterClearSize == 1 : "One item should be cleared from spending list";
        } else {
            data.spendingList.clearAllItems();
            assert data.spendingList.getListSize() == 0 : "List size should be 0";
            ui.printClearAllSpendingList();
        }
    }
}
