package seedu.duke.exceptions;

/**
 * Signals that there is nothing to clear for the repayment list as it is empty.
 */
//@@author xieyaoyue
public class InvalidClearRepaymentException extends DukeException {
    @Override
    public String getMessage() {
        return "Sorry, there's nothing to clear in the repayment list.";
    }
}
