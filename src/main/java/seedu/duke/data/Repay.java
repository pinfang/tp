package seedu.duke.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** It is to store the repay information. */
public class Repay {
    private String name;
    private double amount;
    private String symbol;
    private String deadline;
    //@author k-walter
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    
    //@@author killingbear999
    public Repay(String name, String symbol, double amount, String deadline) {
        this.name = name;
        this.symbol = symbol;
        this.amount = amount;
        this.deadline = deadline;
    }

    //@author k-walter
    /** It is to get the deadline of a specific item. */
    public LocalDate getDeadline() {
        return LocalDate.parse(deadline, format);
    }
    
    //@@author killingbear999
    /** It is to form the string that combines all the information. */
    public String toString() {
        return name + " " + symbol + " " + String.format("%.2f", amount) + " " + deadline;
    }
}

