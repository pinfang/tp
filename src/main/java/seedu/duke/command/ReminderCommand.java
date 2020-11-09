package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

//@@author pinfang

/**
 * Reminds the user the amount spent during current week.
 */
public class ReminderCommand extends Command {
    private LocalDate startWeek;
    private WarnCommand warn;
    private ArrayList<String> week = new ArrayList<>();
    private String currency = "SGD";

    public ReminderCommand() {
        saveDatesToList();
        warn = new WarnCommand();
    }

    /**
     * Summarises the total amount spent during the current week.
     * Shows amount of budget left.
     * Provides warning if exceeding the budget.
     * @param data This parameter contains the spending list.
     * @param ui This parameter will prints out necessary messages shown to user.
     */
    @Override
    public void execute(Data data, Ui ui) {
        updateCurrency(data);
        double amountRemained = 0;

        if (data.budget.hasBudget) {
            warn.execute(data, ui);
            amountRemained = findRemainingAmount(data);
        }

        double totalAmountSpent = 0;
        for (String i: week) {
            totalAmountSpent += data.spendingList.getSpendingAmountTime(i);
        }

        ui.printReminderMessage(currency, totalAmountSpent, amountRemained, startWeek.toString());
    }

    /**
     * This method notes down the current date and finds the date that is Monday.
     * @return This returns the date on Monday
     */
    private LocalDate startOfWeek() {
        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();
        switch (day) {
        case MONDAY:
            startWeek = today;
            break;
        case TUESDAY:
            startWeek = today.minusDays(1);
            break;
        case WEDNESDAY:
            startWeek = today.minusDays(2);
            break;
        case THURSDAY:
            startWeek = today.minusDays(3);
            break;
        case FRIDAY:
            startWeek = today.minusDays(4);
            break;
        case SATURDAY:
            startWeek = today.minusDays(5);
            break;
        case SUNDAY:
            startWeek = today.minusDays(6);
            break;
        default:
            break;
        }

        return startWeek;
    }

    /**
     * This method saves the 7 dates of the current week into a list.
     */
    private void saveDatesToList() {
        LocalDate startDay = startOfWeek();
        for (int i = 0; i < 7; i++) {
            week.add(startDay.minusDays(-i).toString());
        }
    }

    /**
     * This method is to find the amount of budget left.
     * @param data This is the parameter that contains the budget.
     * @return This returns the amount of budget left.
     */
    private double findRemainingAmount(Data data) {
        double budgetLimit = data.budget.getBudgetLimit();
        double currentAmount = data.spendingList.getCurrentAmount(data);
        return budgetLimit - currentAmount;
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
