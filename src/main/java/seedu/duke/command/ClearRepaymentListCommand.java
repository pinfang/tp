package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidClearRepaymentException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.ui.Ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command for clearing the repayment list.
 */
//@@author xieyaoyue
public class ClearRepaymentListCommand extends Command {
    public boolean isClearAll;
    public Integer clearIndex;
    private static Logger logger = Logger.getLogger("ClearRepaymentListCommand");

    public ClearRepaymentListCommand(boolean isClearAll, Integer clearIndex) {
        this.isClearAll = isClearAll;
        this.clearIndex = clearIndex;
    }

    /**
     * Clears the repayment list by either clearing a single entry or the whole list.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     * @throws IOException if the application fails to clear the repayment list
     * @throws InvalidClearRepaymentException if the repayment list is already empty
     * @throws InvalidNumberException if the user inputs an invalid index of the repayment list
     */
    public void execute(Data data, Ui ui) throws IOException,
            InvalidClearRepaymentException, InvalidNumberException {
        int size = data.repaymentList.getListSize();
        if (size == 0) {
            throw new InvalidClearRepaymentException();
        }
        if (clearIndex > size) {
            logger.log(Level.FINE, "clearIndex is invalid");
            throw new InvalidNumberException();
        }
        logger.log(Level.FINE, "going to clear repayment list");
        if (!isClearAll) {
            int beforeClearSize = data.repaymentList.getListSize();
            ui.printClearIndex(data.repaymentList.getEntry(clearIndex - 1));
            data.repaymentList.deleteRepaymentEntry(clearIndex - 1);
            int afterClearSize = data.repaymentList.getListSize();
            assert beforeClearSize - afterClearSize == 1 : "One item should be cleared from repayment list";
        } else {
            data.repaymentList.clearAllEntries();
            assert data.repaymentList.getListSize() == 0 : "List size should be 0";
            ui.printClearAllRepaymentList();
        }
    }
}
