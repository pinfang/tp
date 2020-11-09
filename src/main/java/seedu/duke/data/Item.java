package seedu.duke.data;

import java.time.LocalDate;

//@@author pinfang

/**
 * This is to save the details of an item spent.
 */
public class Item {
    private String description;
    private double amount;
    private String symbol;
    private String date;
    private String category;

    //@author k-walter
    public Item(String description, String symbol, double amount) {
        this(description, symbol, amount, null);
    }

    //@author k-walter
    public Item(String description, String symbol, double amount, String category) {
        init(description, symbol, amount, category, currentDate());
    }

    //@@author k-walter
    public Item(String description, String symbol, double amount, String category, String date) {
        init(description, symbol, amount, category, date);
    }

    //@author k-walter
    protected void init(String description, String symbol, double amount, String category, String date) {
        this.description = description;
        this.symbol = symbol;
        this.amount = amount;
        this.category = Category.categoryName(category);
        this.date = date;
    }

    //@@author pinfang
    private String currentDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    /**
     * Edits the description of item.
     * @param description This is the new description to be saved.
     */
    public void editDescription(String description) {
        this.description = description;
    }

    /**
     * Edits the amount of spent on an item.
     * @param amount This is the new amount spent.
     */
    public void editAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Edits the currency symbol of the item.
     * @param symbol This is the new currency symbol.
     */
    public void editSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Edits the category of the item.
     * @param category This is the new category of the item.
     */
    public void editCategory(String category) {
        this.category = Category.categoryName(category);
    }

    /**
     * Gets the currency symbol of the item.
     * @return This returns the currency symbol of the item.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the description of the item.
     * @return This returns the description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the amount spent on the item.
     * @return This returns the amount spent on the item.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the date that the item is spent.
     * @return This returns the date that the item is spent
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the category of the item.
     * @return This returns the category name of the item.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Edits the date of the spending item.
     * @param specificDate This is the new date to be saved.
     */
    public void editDate(String specificDate) {
        this.date = specificDate;
    }

    /**
     * Prints out the details of the item.
     * @return Details of the item.
     */
    //@@author k-walter
    @Override
    public String toString() {
        return String.format("%s [%s] %s %s %s", date, category, description, symbol, String.format("%.2f", amount));
    }
}
