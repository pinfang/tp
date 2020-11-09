package seedu.duke.data;

import seedu.duke.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents the budget limit set by the user.
 */
//@@author xieyaoyue
public class Budget {
    private double budgetLimit;
    private String currency = "SGD";
    public boolean hasBudget;
    public String date = "1000-01-01";
    public Storage storage;

    public Budget(Storage storage) {
        this.storage = storage;
    }

    public Budget() {
        this(null);
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDate() {
        return date;
    }

    public void addBudget(String currency, double budgetLimit) throws IOException {
        this.currency = currency;
        this.budgetLimit = budgetLimit;
        hasBudget = true;
        date = currentDate();
        save();
    }

    public void updateBudget(String outputCurrency, double newBudgetLimit) throws IOException {
        currency = outputCurrency;
        budgetLimit = newBudgetLimit;
        date = currentDate();
        save();
    }

    public void clearBudget() throws IOException {
        currency = "SGD";
        budgetLimit = 0;
        hasBudget = false;
        save();
    }

    protected String currentDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public void save() throws IOException {
        if (storage == null) {
            return;
        }
        storage.save(this);
    }
}
