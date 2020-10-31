package seedu.duke.command;

import seedu.duke.data.RepaymentList;
import seedu.duke.data.SpendingList;
import seedu.duke.Ui;

public class HelpCommand extends Command {

    @Override
    public void execute(SpendingList spendingList, RepaymentList repaymentList, Ui ui) {
        ui.printHelp();
    }
}
