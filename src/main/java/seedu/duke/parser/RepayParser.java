package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.Command;
import seedu.duke.command.RepayCommand;
import seedu.duke.exceptions.InvalidCommandException;

//@author k-walter
public class RepayParser extends Parser {
    public RepayParser() {
        addSpendingOption(true);
        addDescriptionOption(true);
        addDateOption();
    }

    @Override
    public Command parse(String[] args) throws ParseException, InvalidCommandException {
        CommandLine line = getCommandLine(args);
        Spending s = parseSpendingOption(line);
        String description = parseDescriptionOption(line);
        String date = parseDateOption(line);
        return new RepayCommand(description, s.symbol, s.amount, date);
    }
}
