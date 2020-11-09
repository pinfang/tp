package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.DecimalFormatter;

//@@author killingbear999
/** It is to warn the user when the spending approaches budget limit or its threshold value. */
public class WarnCommand extends Command {
    private double budgetLimit;
    private double budgetThreshold;
    private String outputCurrency;
    private final double threshold = 0.9;
    
    public WarnCommand() {
    }
    
    public void execute(Data data, Ui ui) {
        outputCurrency = data.budget.getCurrency();
        budgetLimit = data.budget.getBudgetLimit();
        budgetThreshold = budgetLimit * threshold;
        double currentAmount = data.spendingList.getCurrentAmount(data);
        if (currentAmount >= budgetThreshold && currentAmount < budgetLimit) {
            DecimalFormatter decimalFormatter = new DecimalFormatter();
            double amountRemaining = budgetLimit - currentAmount;
            amountRemaining = decimalFormatter.convert(amountRemaining);
            ui.printApproachingWarningMessage(outputCurrency, amountRemaining);
        } else if (currentAmount >= budgetLimit) {
            ui.printExceedingWarningMessage();
        }
    }
}
