package seedu.duke.data;

import seedu.duke.command.ConvertCommand;
import seedu.duke.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This is to store a list of items spent by the user.
 */
public class SpendingList {
    private String description;
    public Storage storage;

    public ArrayList<Item> spendingList;

    public SpendingList(String description, ArrayList<Item> spendingList, Storage storage) {
        this.description = description;
        this.spendingList = spendingList;
        this.storage = storage;
    }
    
    public SpendingList() {
        spendingList = new ArrayList<>();
    }

    public SpendingList(String description, Storage storage) {
        this(description, new ArrayList<>(), storage);
    }

    public SpendingList(Storage storage) {
        this("", storage);
    }

    public SpendingList(ArrayList<Item> spendingList) {
        this("", spendingList, null);
    }

    private void save() throws IOException {
        if (storage == null) {
            return;
        }
        storage.save(this);
    }

    //@@author pinfang
    /**
     * Adds an item.
     * @param description This is the description of the item added.
     * @param symbol This is the currency symbol of the item added.
     * @param amount This is the amount spent on the item added.
     * @param category This is the category of the item.
     * @throws IOException If there is error in storage.
     */
    public void addItem(String description, String symbol, double amount, String category) throws IOException {
        Item item = new Item(description, symbol, amount, category);
        spendingList.add(item);
        save();
    }

    /**
     * Adds an item.
     * @param description This is the description of the item added.
     * @param symbol This is the currency symbol of the item added.
     * @param amount This is the amount spent on the item added.
     * @throws IOException If there is error in storage.
     */
    public void addItem(String description, String symbol, double amount) throws IOException {
        Item item = new Item(description, symbol, amount);
        spendingList.add(item);
        save();
    }

    /**
     * Deletes a specific item in the list.
     * @param index This is the item to be deleted.
     * @throws IOException If there is error in storage.
     */
    public void deleteItem(int index) throws IOException {
        spendingList.remove(index);
        save();
    }

    /**
     * Deletes all items in the list.
     * @throws IOException If there is error in storage.
     */
    public void clearAllItems() throws IOException {
        spendingList.clear();
        assert getListSize() == 0 : "list size should be 0";
        save();
    }

    /**
     * Gets a specific spending item.
     * @param index This is the item to get.
     * @return The item to be gotten.
     */
    public Item getItem(int index) {
        return spendingList.get(index);
    }

    /**
     * Gets the size of the spending list.
     * @return The total number of items saved in the list.
     */
    public int getListSize() {
        return spendingList.size();
    }

    /**
     * Gets the whole spending list.
     * @return The whole spending list.
     */
    public ArrayList<Item> getSpendingList() {
        return spendingList;
    }

    /**
     * Gets the total amount spent in a given time.
     * @param period This is the period of time.
     * @return total amount spent in a given time.
     */
    public double getSpendingAmountTime(String period) {
        double totalAmount = 0;
        for (Item i: spendingList) {
            if (i.getDate().contains(period)) {
                totalAmount += i.getAmount();
            }
        }
        return totalAmount;
    }

    //@@author k-walter
    public ArrayList<Item> filterSpendingList(String category, String period) {
        return spendingList.stream()
            .filter(i -> category == null || i.getCategory().matches("(?i).*" + category + ".*"))
            .filter(i -> period == null || i.getDate().matches("^" + period + ".*"))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    //@@author k-walter
    public double getSpendingAmountCategory(String category, String period) {
        return filterSpendingList(category, period).stream()
            .mapToDouble(Item::getAmount)
            .sum();
    }
    
    //@@author killingbear999
    public void editItemDescription(int number, String description)
            throws IOException {
        Item item = getItem(number);
        item.editDescription(description);
        save();
    }
    
    //@@author killingbear999
    public void editItemCategory(int number, String category)
            throws IOException {
        Item item = getItem(number);
        item.editCategory(category);
        save();
    }
    
    //@@author killingbear999
    public void editItemAmount(int number, double amount)
            throws IOException {
        Item item = getItem(number);
        item.editAmount(amount);
        save();
    }

    //@@author killingbear999
    public void updateSpendingList() throws IOException {
        ConvertCommand convertCommand = new ConvertCommand();
        spendingList = convertCommand.updateSpendingList();
        save();
    }
    
    //@@author killingbear999
    public double getCurrentAmount(Data data) {
        double currentAmount = 0;
        for (Item i: spendingList) {
            if (i.getDate().compareTo(data.budget.getDate()) >= 0) {
                currentAmount += i.getAmount();
            }
        }
        return currentAmount;
    }
    
    //@@author killingbear999
    private void swapItem(Item item1, Item item2) {
        final String tempCategory = item1.getCategory();
        final String tempDescription = item1.getDescription();
        final String tempSymbol = item1.getSymbol();
        final String tempDate = item1.getDate();
        final double tempAmount = item1.getAmount();
        
        item1.editCategory(item2.getCategory());
        item1.editDescription(item2.getDescription());
        item1.editSymbol(item2.getSymbol());
        item1.editAmount(item2.getAmount());
        item1.editDate(item2.getDate());
    
        item2.editCategory(tempCategory);
        item2.editDescription(tempDescription);
        item2.editSymbol(tempSymbol);
        item2.editAmount(tempAmount);
        item2.editDate(tempDate);
    }
    
    //@@author killingbear999
    public void categoriseSpendingList() {
        int count = spendingList.size() - 1;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < count + 1; j++) {
                Item currentItem = getItem(j);
                if (i == 0 && currentItem.getCategory().equals("Education")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 1 && currentItem.getCategory().equals("Entertainment")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 2 && currentItem.getCategory().equals("Food")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 3 && currentItem.getCategory().equals("Health")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 4 && currentItem.getCategory().equals("Transportation")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 5 && currentItem.getCategory().equals("Utilities")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                } else if (i == 6 && currentItem.getCategory().equals("Other")) {
                    Item lastItem = getItem(count);
                    swapItem(currentItem, lastItem);
                    count = count - 1;
                }
            }
        }
    }
}
