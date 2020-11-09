package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.data.Item;
import seedu.duke.exceptions.EmptyListException;
import seedu.duke.exceptions.InvalidCurrencyException;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.InvalidOutputCurrencyException;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.DecimalFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author killingbear999
/** This class is to convert the currency to another one. */
public class ConvertCommand extends Command {
    private String currencies;
    private String outputCurrency;
    private String inputCurrency;
    private double exchangeRate = 1.0;
    public static ArrayList<Item> newSpendingList = new ArrayList<>();
    private static Logger logger = Logger.getLogger("ConvertCommand");

    /** SGD to USD; USD to SGD; SGD to CNY; CNY to SGD. */
    private final String[][] exchangeRates = {
            {"SGDUSD", "USDSGD", "SGDCNY", "CNYSGD"},
            {"0.74", "1.35135", "5.00", "0.20"},
    };


    public ConvertCommand() {
    }

    public ConvertCommand(String source, String target) {
        this.inputCurrency = source;
        this.outputCurrency = target;
    }

    private String identifyCurrency() {
        return inputCurrency + outputCurrency;
    }

    /** It is to identify the exchange rate for the corresponding input and output currencies. */
    private void findExchangeRate() {
        for (int i = 0; i < 4; i++) {
            if (exchangeRates[0][i].equals(currencies)) {
                exchangeRate = Double.parseDouble(exchangeRates[1][i]);
                assert exchangeRate == Double.parseDouble(exchangeRates[1][i]) : "Incorrect exchange rate";
                break;
            }
        }
    }
    
    /** It is to check if the input and output currencies are valid. */
    private boolean isValid() {
        for (int i = 0; i < 4; i++) {
            if (exchangeRates[0][i].equals(currencies)) {
                return true;
            }
        }
        return false;
    }
    
    /** It is to convert the currency for the whole spending list.
     *
     * @throws InvalidInputCurrencyException If input currency is invalid
     * @throws InvalidOutputCurrencyException If output currency is invalid
     * @throws InvalidCurrencyException If the exchange rate for the pair of currencies are not stored in the system
     * @throws EmptyListException If the spending list is empty and the convert command is called
     */
    @Override
    public void execute(Data data, Ui ui) throws IOException,
            InvalidInputCurrencyException, InvalidOutputCurrencyException, InvalidCurrencyException,
            EmptyListException {
        if (!(outputCurrency.equals("SGD") || outputCurrency.equals("USD") || outputCurrency.equals("CNY"))) {
            throw new InvalidOutputCurrencyException();
        }
        
        if (!(inputCurrency.equals("SGD") || inputCurrency.equals("USD") || inputCurrency.equals("CNY"))) {
            throw new InvalidInputCurrencyException();
        }
        
        int size = data.spendingList.getListSize();
        if (size == 0) {
            throw new EmptyListException();
        }
        
        String defaultCurrency = data.spendingList.getItem(0).getSymbol();
        
        if (inputCurrency.equals(defaultCurrency)) {
            logger.log(Level.FINE, "going to start processing");
            newSpendingList = data.spendingList.getSpendingList();
            currencies = identifyCurrency();
            findExchangeRate();
            updateList();
            if (isValid()) {
                ui.printConvertCurrency(outputCurrency);
            } else {
                throw new InvalidCurrencyException();
            }
            data.spendingList.updateSpendingList();
            updateBudgetList(data);
            logger.log(Level.FINE, "end of processing");
        } else {
            ui.printInvalidConversion(defaultCurrency);
        }
    }
    
    /** It is to update the new amount and currency symbol after conversion for the whole spending list. */
    private void updateList() {
        for (Item item : newSpendingList) {
            if (!item.getSymbol().equals(outputCurrency)) {
                updateNewAmount(item);
                updateCurrency(item);
            }
        }
    }
    
    /** It is to update the new amount for a specific item. */
    private void updateNewAmount(Item currentString) {
        double amount = currentString.getAmount();
        amount = amount * exchangeRate;
        DecimalFormatter decimalFormatter = new DecimalFormatter();
        amount = decimalFormatter.convert(amount);
        currentString.editAmount(amount);
    }

    /** It is to update the new currency symbol for a specific item. */
    private void updateCurrency(Item currentString) {
        switch (currencies) {
        case "SGDUSD":
            currentString.editSymbol("USD");
            break;
        case "USDSGD":
            currentString.editSymbol("SGD");
            break;
        case "SGDCNY":
            currentString.editSymbol("CNY");
            break;
        case "CNYSGD":
            currentString.editSymbol("SGD");
            break;
        default:
        }
    }
    
    /** It is to update the budget limit after conversion. */
    public void updateBudgetList(Data data) throws IOException {
        if (!data.budget.getCurrency().equals(outputCurrency)) {
            double budgetLimit = data.budget.getBudgetLimit();
            double newBudgetLimit = budgetLimit * exchangeRate;
            data.budget.updateBudget(outputCurrency, newBudgetLimit);
        }
    }
    
    /** It is to update the whole spending list after conversion. */
    public ArrayList<Item> updateSpendingList() {
        return newSpendingList;
    }
}
