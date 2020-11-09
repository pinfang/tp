package seedu.duke.data;

import seedu.duke.exceptions.InvalidStorageFileExtensionException;
import seedu.duke.exceptions.InvalidStorageFilePathException;
import seedu.duke.storage.Storage;

//@author k-walter
public class Data {
    public SpendingList spendingList;
    public RepaymentList repaymentList;
    public Budget budget;

    /**
     * Initialises data used in Duke with default file paths.
     * Instance used as a wrapper for data to be passed between methods
     * @throws InvalidStorageFilePathException if unable to find path to file
     * @throws InvalidStorageFileExtensionException if given file extension is not json
     */
    public Data() throws InvalidStorageFilePathException, InvalidStorageFileExtensionException {
        spendingList = new Storage("data/duke_spending.json").loadSpendingList();
        repaymentList = new Storage("data/duke_repayment.json").loadRepaymentList();
        budget = new Storage("data/duke_budget.json").loadBudget();
    }

    /**
     * Use given data objects, even if null. Used extensively in tests
     * @param spendingList spending list or null
     * @param repaymentList repayment list or null
     * @param budget budget or null
     */
    public Data(SpendingList spendingList, RepaymentList repaymentList, Budget budget) {
        this.spendingList = spendingList;
        if (this.spendingList == null) {
            this.spendingList = new SpendingList();
        }
        this.repaymentList = repaymentList;
        if (this.repaymentList == null) {
            this.repaymentList = new RepaymentList();
        }
        this.budget = budget;
        if (this.budget == null) {
            this.budget = new Budget();
        }
    }
}
