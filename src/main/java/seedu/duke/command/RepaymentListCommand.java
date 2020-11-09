package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

//@@author killingbear999
/** It is to print out the repayment list. */
public class RepaymentListCommand extends Command {
    @Override
    public void execute(Data data, Ui ui) {
        ui.printRepaymentList(data.repaymentList.getRepaymentList());
    }
}
