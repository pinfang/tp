package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.Command;
import seedu.duke.command.EditCommand;
import seedu.duke.exceptions.InvalidCommandException;
import seedu.duke.exceptions.InvalidNumberException;

//@author k-walter
public class EditParser extends Parser {
    public EditParser() {
        super();
        addDescriptionOption(false);
        addCategoryOption(false);
        addSpendingOption(false);
    }

    @Override
    public Command parse(String[] args) throws InvalidNumberException, ParseException, InvalidCommandException {
        CommandLine line = getCommandLine(args, true);

        int index = getIndex(line);
        String description = parseDescriptionOption(line);
        String category = parseCategoryOption(line);
        Spending s = parseSpendingOption(line);

        return new EditCommand(index, description, s.symbol, s.amount, category);
    }

}
