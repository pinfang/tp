package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.InvalidBudgetException;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.AmountConverter;
import seedu.duke.utilities.DecimalFormatter;

import java.io.IOException;

//@@author killingbear999
/** It is to set the budget limit for the spending. */
public class SetBudgetCommand extends Command {

    private double budgetLimit;
    private String currency;
    private String defaultCurrency = "SGD";

    
    public SetBudgetCommand(String currency, Double budgetLimit) {
        this.currency = currency;
        this.budgetLimit = budgetLimit;
    }
    
    /** It is to convert the currency for the whole spending list.
     *
     * @throws InvalidInputCurrencyException If input currency is invalid
     * @throws InvalidBudgetException If the budget input is less than 0.01 or negative
     */
    @Override
    public void execute(Data data, Ui ui)
        throws InvalidInputCurrencyException, InvalidBudgetException, IOException {
        int size = data.spendingList.getListSize();
        if (size != 0) {
            defaultCurrency = data.spendingList.getItem(0).getSymbol();
        }
        if (!(currency.equals("SGD") || currency.equals("USD") || currency.equals("CNY"))) {
            throw new InvalidInputCurrencyException();
        }
        if (!currency.equals(defaultCurrency)) {
            AmountConverter amountConverter = new AmountConverter(currency, budgetLimit, defaultCurrency);
            budgetLimit = amountConverter.updateAmount();
            currency = amountConverter.updateCurrency();
        }
        if (budgetLimit < 0.01) {
            throw new InvalidBudgetException();
        }
        DecimalFormatter decimalFormatter = new DecimalFormatter();
        budgetLimit = decimalFormatter.convert(budgetLimit);
        data.budget.addBudget(currency, budgetLimit);
        ui.printBudgetLimit(data, currency, budgetLimit);
    }
}
