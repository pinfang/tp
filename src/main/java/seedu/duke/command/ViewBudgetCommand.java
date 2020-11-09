package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

//@@author killingbear999

/** It is to view the budget limit that has been input previously. */
public class ViewBudgetCommand extends Command {

    @Override
    public void execute(Data data, Ui ui) {
        if (data.budget.hasBudget) {
            ui.printCurrentBudgetLimit(data);
        } else {
            ui.printNoBudget();
        }
    }
}
