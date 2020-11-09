package seedu.duke.exceptions;

/**
 * Signals that there is nothing to clear for the budget data as it is empty.
 */
//@@author xieyaoyue
public class InvalidClearBudgetException extends DukeException {
    @Override
    public String getMessage() {
        return "Sorry, there's no budget limit set.";
    }
}
