package seedu.duke.data;

//@@author pinfang

/**
 * These are the types of the categories that can be tagged to the spending item.
 */
public enum Category {
    FOOD("Food"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    HEALTH("Health"),
    TRANSPORTATION("Transportation"),
    UTILITIES("Utilities"),
    OTHERS("Others");

    private final String category;
    Category(String category) {
        this.category = category;
    }

    /**
     * This method checks if the category entered by the user is valid.
     * If the category entered is not valid, it will be categorised as others.
     * @param name This is the category entered by the user
     * @return The correct format of the category name that is to be tagged to the item.
     */
    public static String categoryName(String name) {
        if (name == null) {
            return OTHERS.toString();
        }
        for (Category c: values()) {
            if (name.equalsIgnoreCase(c.category)) {
                return c.toString();
            }
        }
        return OTHERS.toString();
    }

    /**
     * Prints the category name.
     * @return Name of the category.
     */
    @Override
    public String toString() {
        return this.category;
    }
}
