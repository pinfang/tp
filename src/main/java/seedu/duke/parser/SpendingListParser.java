package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.Command;
import seedu.duke.command.SpendingListCommand;
import seedu.duke.exceptions.InvalidCommandException;
import seedu.duke.exceptions.InvalidMonthException;
import seedu.duke.exceptions.InvalidYearException;

import java.util.Arrays;

//@author k-walter
public class SpendingListParser extends Parser {
    public SpendingListParser() {
        super();
        addAllOption();
        addCategoryOption(false);
    }

    @Override
    public Command parse(String[] args) throws ParseException, InvalidCommandException, InvalidYearException,
        InvalidMonthException {
        if (args.length < 1 || !args[0].equals("list")) {
            throw new InvalidCommandException();
        }

        String[] opts = Arrays.copyOfRange(args, 1, args.length);
        CommandLine line = getCommandLine(opts, true);
        String category = parseCategoryOption(line);
        if (parseAllOption(line)) {
            parseNoArgs(line);
            return new SpendingListCommand(category);
        }
        YearMonth ym = parseDateArgs(line);
        return new SpendingListCommand(ym.year, ym.month, category);
    }
}
