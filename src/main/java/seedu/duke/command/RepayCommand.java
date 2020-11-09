package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.InvalidAmountException;
import seedu.duke.exceptions.InvalidNameException;
import seedu.duke.exceptions.InvalidDateException;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.DateTimeFormatter;
import seedu.duke.utilities.DecimalFormatter;

import java.io.IOException;

//@@author killingbear999
/** It is to add the repay information into the repayment list. */
public class RepayCommand extends Command {
    private String name;
    private String currency;
    private double repayment;
    private String deadline;

    public RepayCommand(String name, String currency, Double amount, String deadline) {
        this.name = name;
        this.currency = currency;
        this.repayment = amount;
        this.deadline = deadline;
    }
    
    /** It is to add the repay information into the repayment list.
     *
     * @throws InvalidInputCurrencyException If input currency is invalid
     * @throws InvalidAmountException If the amount input is less than 0.01 or negative
     * @throws InvalidNameException If the name input is invalid or does not match with the format
     * @throws InvalidDateException If the date input is invalid or does not match with the specific format
     */
    @Override
    public void execute(Data data, Ui ui) throws IOException,
            InvalidDateException, InvalidAmountException, InvalidNameException, InvalidInputCurrencyException {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter("yyyy-MM-dd");
        if (repayment < 0.01) {
            throw new InvalidAmountException();
        }
        if (!dateTimeFormatter.isValid(deadline)) {
            throw new InvalidDateException();
        }
        if (!isValidName()) {
            throw new InvalidNameException();
        }
        if (!(currency.equals("SGD") || currency.equals("USD") || currency.equals("CNY"))) {
            throw new InvalidInputCurrencyException();
        }
        DecimalFormatter decimalFormatter = new DecimalFormatter();
        repayment = decimalFormatter.convert(repayment);
        data.repaymentList.addItem(name, currency, repayment, deadline);
        ui.printAddRepay(data.repaymentList);
    }
    
    /** It is to check if the name input is valid or matches with the specific format. */
    private boolean isValidName() {
        return name.matches(".*[a-zA-Z]+.*");
    }
}
