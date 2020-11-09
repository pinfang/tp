package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.exceptions.InvalidCommandException;

//@author k-walter
public class AddParser extends Parser {
    public AddParser() {
        super();
        addDescriptionOption(true);
        addCategoryOption(false);
        addSpendingOption(true);
    }

    public Command parse(String[] args) throws ParseException, InvalidCommandException {
        CommandLine line = getCommandLine(args);
        String category = parseCategoryOption(line);
        Spending spending = parseSpendingOption(line);
        String description = parseDescriptionOption(line);
        return new AddCommand(description, spending.symbol, spending.amount, category);
    }

}
