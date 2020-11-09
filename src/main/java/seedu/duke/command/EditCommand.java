package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.EmptyListException;
import seedu.duke.exceptions.InvalidAmountException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.InvalidNameException;
import seedu.duke.exceptions.EmptyCommandException;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.AmountConverter;
import seedu.duke.utilities.DecimalFormatter;

import java.io.IOException;

//@@author killingbear999
/** This class is to edit the item in the spending list. */
public class EditCommand extends Command {
    public String description;
    public Double amount;
    public String currency;
    public int index;
    public String category;
    private String defaultCurrency = "SGD";

    public EditCommand(int index, String description, String currency, Double amount, String category) {
        this.index = index - 1;
        this.description = description;
        this.currency = currency;
        this.amount = amount;
        this.category = category;
    }
    
    /** It is to convert the currency for the whole spending list.
     *
     * @throws InvalidInputCurrencyException If input currency is invalid
     * @throws EmptyListException If the spending list is empty and the convert command is called
     * @throws InvalidAmountException If the amount input is less than 0.01 or negative
     * @throws InvalidNumberException If the index input is out of bound
     * @throws InvalidNameException If the name input is invalid or does not match with the format
     * @throws EmptyCommandException If no command is given except for the index
     */
    @Override
    public void execute(Data data, Ui ui) throws IOException,
            InvalidAmountException, InvalidNumberException, InvalidInputCurrencyException, InvalidNameException,
            EmptyListException, EmptyCommandException {
        
        if (description == null && amount == null && category == null) {
            throw new EmptyCommandException();
        }
    
        if (data.spendingList.getListSize() == 0) {
            throw new EmptyListException();
        } else if (index >= data.spendingList.getListSize() && data.spendingList.getListSize() != 0) {
            throw new InvalidNumberException();
        }
        
        if (description != null) {
            if (!isValidName()) {
                throw new InvalidNameException();
            }
            data.spendingList.editItemDescription(index, description);
            ui.printEdit(data.spendingList, index);
        }
        
        if (amount != null) {
            int size = data.spendingList.getListSize();
            if (size != 0) {
                defaultCurrency = data.spendingList.getItem(0).getSymbol();
            }
            if (!currency.equals(defaultCurrency)) {
                AmountConverter amountConverter = new AmountConverter(currency, amount, defaultCurrency);
                amount = amountConverter.updateAmount();
                currency = amountConverter.updateCurrency();
            }
            if (!(currency.equals("SGD") || currency.equals("USD") || currency.equals("CNY"))) {
                throw new InvalidInputCurrencyException();
            }
            if (amount < 0.01) {
                throw new InvalidAmountException();
            }
            String defaultCurrency = data.spendingList.getItem(0).getSymbol();
            if (currency.equals(defaultCurrency)) {
                DecimalFormatter decimalFormatter = new DecimalFormatter();
                amount = decimalFormatter.convert(amount);
                data.spendingList.editItemAmount(index, amount);
                ui.printEdit(data.spendingList, index);
            } else {
                ui.printInvalidConversion(defaultCurrency);
            }
        }
        
        if (category != null) {
            data.spendingList.editItemCategory(index, category);
            ui.printEdit(data.spendingList, index);
        }
    }
    
    /** It is to check if the name input is valid. */
    private boolean isValidName() {
        return description.matches(".*[a-zA-Z]+.*");
    }
}
