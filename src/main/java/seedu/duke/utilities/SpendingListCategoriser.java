package seedu.duke.utilities;

import seedu.duke.data.SpendingList;

//@@author killingbear999
/** It is to categorise the spending list. */
public class SpendingListCategoriser {
    
    public SpendingListCategoriser() {
    }
    
    public void execute(SpendingList spendingList) {
        spendingList.categoriseSpendingList();
    }
    
}
