package seedu.duke;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Ui {
    private Scanner in;
    private PrintStream out;
    private static final String SEPARATE_LINE_CHAR = "-";
    private static final int SEPARATE_LINE_LENGTH = 104;
    private static final String LOGO = "  _____         __ _      ___              \n"
            + " / ___/__ ___  / /| | /| / (_)__ ___       \n"
            + "/ /__/ -_) _ \\/ __/ |/ |/ / (_-</ -_)      \n"
            + "\\___/\\__/_//_/\\__/|__/|__/_/___/\\__/       \n"
            + "  / _ \\___  / / /__ ____| | /| / (_)__ ___ \n"
            + " / // / _ \\/ / / _ `/ __/ |/ |/ / (_-</ -_)\n"
            + "/____/\\___/_/_/\\_,_/_/  |__/|__/_/___/\\__/ \n";
    private static final String BORDER_CORNER = "+";
    private static final String BORDER_HORIZONTAL = "-";
    private static final String BORDER_VERTICAL = "|";
    private static final int TABLE_SIZE = 104;
    private static final String[][] TABLE_OF_COMMANDS = {
            {"ACTION", "FORMAT", "EXAMPLES (IF ANY)"},
            {"add", "add -d DESCRIPTION -s SPENDING [-f SKIP CONFIRMATION]", "add -d chicken rice -s $3.00 -f"},
            {"clear", "clear INDEX", "clear 1"},
            {"", "OR clear -all", ""},
            {"convert", "convert -d DESCRIPTION -d DESCRIPTION", "convert -d SGD -d USD"},
            {"edit", "edit INDEX [-d NEW_DESCRIPTION] [-s NEW_SPENDING]", "edit 1 -d buy grocery -s $15"},
            {"help", "help", ""},
            {"list", "list", ""},
            {"", "OR list YEAR", "list 2020"},
            {"", "OR list YEAR MONTH", "list 2020 July"},
            {"", "OR list -all", ""},
            {"logout", "logout", ""},
            {"summary", "summary", ""},
            {"", "OR summary [YEAR]", "summary 2020"},
            {"", "OR summary [YEAR] [MONTH]", "summary 2020 July"},
            {"", "OR summary -all", ""}
    };

    public Ui() {
        this(new Scanner(System.in), System.out);
    }

    public Ui(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String getUserInput() {
        return in.nextLine();
    }

    public void printMessage(String message) {
        out.println(message);
        drawSeparateLine();
    }

    public void printWelcomeMessage() {
        drawSeparateLine();
        out.println(LOGO);
        drawSeparateLine();
    }

    public void printWelcomeMessage(String filePath) {
        printWelcomeMessage();
        out.println("Local file path: " + filePath);
        drawSeparateLine();
    }

    public void printGoodbyeMessage() {
        out.println("See you next time!");
        drawSeparateLine();
    }

    private void drawSeparateLine() {
        out.println(SEPARATE_LINE_CHAR.repeat(SEPARATE_LINE_LENGTH));
    }

    public String getSpendingList(SpendingList spendingList) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        spendingList.getSpendingList()
                .forEach(ps::println);
        return os.toString(StandardCharsets.UTF_8);
    }

    public void printSpendingList(SpendingList spendingList) {
        String spendingString = getSpendingList(spendingList);
        if (!spendingString.isEmpty()) {
            out.print(spendingString);
        } else {
            out.println("Nothing in the list.");
        }
        drawSeparateLine();
    }

    private void printTopBottomBorder() {
        out.println(BORDER_CORNER + BORDER_HORIZONTAL.repeat(TABLE_SIZE - 2) + BORDER_CORNER);
    }

    private void printWithinTableBorder() {
        System.out.println(BORDER_HORIZONTAL.repeat(TABLE_SIZE));
    }

    public void printHelp() {
        out.println("Here is a summary of the commands you can use:");
        printTopBottomBorder();
        for (int i = 0; i < 16; i++) {
            out.format("%1s%-10s%1s%-55s%1s%-35s%1s\n", BORDER_VERTICAL, TABLE_OF_COMMANDS[i][0],
                    BORDER_VERTICAL, TABLE_OF_COMMANDS[i][1], BORDER_VERTICAL, TABLE_OF_COMMANDS[i][2],
                    BORDER_VERTICAL);
            if (i == 0 || i == 1 || i == 3 || i == 4 || i == 5 || i == 6 || i == 10 || i == 11) {
                printWithinTableBorder();
            }
        }
        printTopBottomBorder();
    }

    public void printClearIndex(Item item) {
        out.println("You've deleted the record:");
        out.println(item);
        drawSeparateLine();
    }

    public void printClearAll() {
        out.println("You've deleted all the records.");
        drawSeparateLine();
    }

    public void printAdd(SpendingList spendingList) {
        out.println("You've added the record:");
        out.println(spendingList.getItem(spendingList.getListSize() - 1));
        drawSeparateLine();
    }
    
    public void printConvertCurrency(String outputCurrency) {
        out.println("The currency has been changed to " + outputCurrency + " .");
        drawSeparateLine();
    }
    
    public void printEdit(SpendingList spendingList, int index) {
        out.println("You've updated the record:");
        out.println(spendingList.getItem(index));
        drawSeparateLine();
    }

    public void printSummaryMessage(double amount) {
        out.println(String.format("You've spent $%f.", amount));
        drawSeparateLine();
    }

    public void printErrorMessage(String message) {
        out.println(message);
        drawSeparateLine();
    }
    
    public void printBudgetLimit(String currency, double budgetLimit) {
        out.println("The budget limit has been set to " + currency + " " + budgetLimit);
        drawSeparateLine();
    }
    
    public void printApproachingWarningMessage(String outputCurrency, double amountRemaining) {
        out.println("Warning! Your spending is approaching your budget limit.");
        out.println("You still have " + outputCurrency + " " + String.format("%.2f", amountRemaining)
                            + " remained for spending.");
        drawSeparateLine();
    }
    
    public void printExceedingWarningMessage() {
        out.println("Warning! Your spending has exceeded your budget limit.");
        drawSeparateLine();
    }
}
