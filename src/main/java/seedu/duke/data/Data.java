package seedu.duke.data;

import seedu.duke.exceptions.InvalidStorageFileExtensionException;
import seedu.duke.exceptions.InvalidStorageFilePathException;
import seedu.duke.storage.Storage;

import java.io.FileNotFoundException;

//@author k-walter
public class Data {
    public SpendingList spendingList;
    public RepaymentList repaymentList;
    public Budget budget;

    public Data() throws FileNotFoundException, InvalidStorageFilePathException, InvalidStorageFileExtensionException {
        spendingList = new Storage("data/duke_spending.json").loadSpendingList();
        repaymentList = new Storage("data/duke_repayment.json").loadRepaymentList();
        budget = new Storage("data/duke_budget.json").loadBudget();
    }

    public Data(SpendingList sl, RepaymentList rl, Budget b) {
        spendingList = sl;
        if (spendingList == null) {
            spendingList = new SpendingList();
        }
        repaymentList = rl;
        if (repaymentList == null) {
            repaymentList = new RepaymentList();
        }
        budget = b;
        if (budget == null) {
            budget = new Budget();
        }
    }
}
