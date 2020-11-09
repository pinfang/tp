package seedu.duke.command;

import seedu.duke.data.Data;
import seedu.duke.exceptions.EmptyCommandException;
import seedu.duke.exceptions.EmptyListException;
import seedu.duke.exceptions.InvalidAmountException;
import seedu.duke.exceptions.InvalidBudgetException;
import seedu.duke.exceptions.InvalidClearBudgetException;
import seedu.duke.exceptions.InvalidClearRepaymentException;
import seedu.duke.exceptions.InvalidClearSpendingException;
import seedu.duke.exceptions.InvalidCurrencyException;
import seedu.duke.exceptions.InvalidDateException;
import seedu.duke.exceptions.InvalidInputCurrencyException;
import seedu.duke.exceptions.InvalidMonthException;
import seedu.duke.exceptions.InvalidNameException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.InvalidOutputCurrencyException;
import seedu.duke.exceptions.InvalidYearException;
import seedu.duke.ui.Ui;

import java.io.IOException;

/**
 * Represents the command that the user wants the program to execute.
 */
//@@author xieyaoyue
public abstract class Command {
    public abstract void execute(Data data, Ui ui) throws IOException,
            InvalidMonthException, InvalidClearRepaymentException, InvalidClearSpendingException,
            InvalidClearBudgetException, InvalidAmountException, InvalidInputCurrencyException,
            InvalidOutputCurrencyException, InvalidCurrencyException, EmptyListException, InvalidDateException,
            InvalidNameException, InvalidBudgetException, InvalidNumberException, EmptyCommandException,
            InvalidYearException;

    /**
     * Checks whether the application should exit after the command is executed.
     *
     * @return true if the user wants to end program, false if otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
