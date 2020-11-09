package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidAmountException;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.InvalidNameException;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.AmountConverter;
import seedu.duke.utilities.DecimalFormatter;
import seedu.duke.utilities.SpendingListCategoriser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a spending entry to the spending list.
 */
public class AddCommand extends Command {
    public String description;
    public double amount;
    public String currency;
    public String category;
    private String defaultCurrency = "SGD";
    private static Logger logger = Logger.getLogger("AddCommand");

    //@@author xieyaoyue
    public AddCommand(String description, String currency, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
    }
    
    //@@author killingbear999
    @Override
    public void execute(Data data, Ui ui) throws IOException,
            InvalidInputCurrencyException, InvalidAmountException, InvalidNameException {
        logger.log(Level.FINE, "going to add item");
        int size = data.spendingList.getListSize();
        if (size != 0) {
            defaultCurrency = data.spendingList.getItem(0).getSymbol();
        }
        if (!(currency.equals("SGD") || currency.equals("USD") || currency.equals("CNY"))) {
            throw new InvalidInputCurrencyException();
        }
        if (!currency.equals(defaultCurrency)) {
            AmountConverter amountConverter = new AmountConverter(currency, amount, defaultCurrency);
            amount = amountConverter.updateAmount();
            currency = amountConverter.updateCurrency();
        }
        if (!isValidName()) {
            throw new InvalidNameException();
        }
        if (amount < 0.01) {
            throw new InvalidAmountException();
        }
        
        DecimalFormatter decimalFormatter = new DecimalFormatter();
        amount = decimalFormatter.convert(amount);
        data.spendingList.addItem(description, currency, amount, category);
        ui.printAdd(data.spendingList);
        
        /*if (size > 1) {
            SpendingListCategoriser spendingListCategoriser = new SpendingListCategoriser();
            spendingListCategoriser.execute(data.spendingList);
        }*/
        
        if (size > 0 && size % 4 == 0) {
            EncouragementCommand encouragementCommand = new EncouragementCommand();
            encouragementCommand.execute(data, ui);
        }
        
        if (data.budget.hasBudget) {
            WarnCommand warnCommand = new WarnCommand();
            warnCommand.execute(data, ui);
        }
    }
    
    //@@author killingbear999
    private boolean isValidName() {
        return description.matches(".*[a-zA-Z]+.*");
    }
}
