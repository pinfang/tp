package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.data.Data;
import seedu.duke.data.Item;
import seedu.duke.data.SpendingList;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;

//@@author Wu-Haitao
class ExportCommandTest {
    SpendingList spendingList;
    Ui ui = new Ui();

    private ArrayList<Item> initList(Item... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private void initSpendingList() {
        Item[] items = new Item[]{new Item("Item0", "USD", 1.5),
            new Item("Item1", "USD", 21, "Food"),
            new Item("Item2", "USD", 13.14, "Education"),
            new Item("Item3", "USD", 12, "Transportation"),
            new Item("Item4", "USD", 28.0, "Food"),
            new Item("Item5", "USD", 156.7, "Entertainment")};
        this.spendingList =  new SpendingList(initList(items));
    }

    @Test
    void execute() {
        initSpendingList();
        try {
            Data data = new Data(null, null, null);
            new ExportCommand(System.getProperty("user.dir") + "\\", false).execute(data, ui);
        } catch (Exception e) {
            assert false;
        }
        assert true;
    }
}