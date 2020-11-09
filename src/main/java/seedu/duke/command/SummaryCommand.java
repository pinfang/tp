package seedu.duke.command;

import seedu.duke.data.Category;
import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author pinfang

/**
 * Summaries the amount spent during a given period of time.
 * Also summaries amount spent in each category.
 */
public class SummaryCommand extends DateCommand {
    private final String period;
    private String currency = "SGD";
    private static final Logger logger = Logger.getLogger("SummaryCommand");

    public SummaryCommand(String year, String month) {
        if (month == null) {
            period = year;
        } else {
            period = year + "-" + month;
        }
    }

    public SummaryCommand() {
        period = "";
    }

    /**
     * Summarises the total amount spent.
     * @param data This parameter contains the spending list.
     * @param ui This parameter will prints out necessary messages shown to user.
     */
    @Override
    public void execute(Data data, Ui ui) {
        logger.log(Level.FINE, "going to start processing");
        updateCurrency(data);
        double amountSpent = data.spendingList.getSpendingAmountTime(period);
        logger.log(Level.FINE, "end of processing");
        ui.printSummaryMessage(currency, amountSpent);
        for (Category c : Category.values()) {
            double categoryAmountSpent = data.spendingList.getSpendingAmountCategory(c.toString(), period);
            ui.printSummaryCategory(currency, c.name(), categoryAmountSpent);
        }
    }

    /**
     * This method is used to update the current currency symbol.
     * If there is spending item in the spending list, the currency symbol of that item will be used,
     * else SGD will be used.
     * @param data this is parameter that contains the spending list
     */
    private void updateCurrency(Data data) {
        if (data.spendingList.getListSize() > 0) {
            currency = data.spendingList.getItem(0).getSymbol();
        }
    }
}
