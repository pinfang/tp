package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.Command;
import seedu.duke.command.DrawCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ExportCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.PurgeDataCommand;
import seedu.duke.command.RepaymentListCommand;
import seedu.duke.command.SummaryCommand;
import seedu.duke.command.ViewBudgetCommand;
import seedu.duke.exceptions.InvalidCommandException;
import seedu.duke.exceptions.InvalidFormatException;
import seedu.duke.exceptions.InvalidNumberException;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public abstract class Parser {
    protected static CommandLineParser parser;
    protected static Options options;

    protected static void parseNoArgs(CommandLine line) throws InvalidCommandException {
        String[] args = line.getArgs();
        if (args != null && args.length > 0) {
            throw new InvalidCommandException();
        }
    }

    protected static void addAllOption() {
        Option all = Option.builder("a")
            .longOpt("all")
            .argName("all")
            .build();

        options.addOption(all);
    }

    protected static boolean parseAllOption(CommandLine line) {
        return line.hasOption("a");
    }

    protected static YearMonth parseDateArgs(CommandLine line) throws InvalidCommandException {
        String[] period = line.getArgs();
        if (period == null) {
            throw new InvalidCommandException();
        }

        LocalDate now = LocalDate.now();
        YearMonth ym = new YearMonth();
        switch (period.length) {
        case 0:
            ym.month = now.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            ym.year = Integer.toString(now.getYear());
            break;
        case 2:
            ym.year = period[0];
            ym.month = period[1];
            break;
        case 1:
            ym.year = period[0];
            break;
        default:
            throw new InvalidCommandException();
        }
        return ym;
    }

    protected static void addCategoryOption(boolean required) {
        Option category = Option.builder("c")
            .longOpt("category")
            .hasArgs()
            .required(required)
            .argName("category")
            .build();

        options.addOption(category);
    }

    protected static String parseCategoryOption(CommandLine line) {
        return line.getOptionValue("c");
    }

    protected static void addSpendingOption(boolean required) {
        Option spending = Option.builder("s")
            .longOpt("spending")
            .hasArgs()
            .argName("spending")
            .required(required)
            .build();

        options.addOption(spending);
    }

    protected static Spending parseSpendingOption(CommandLine line) throws InvalidFormatException {
        Spending s = new Spending();
        if (line.hasOption("s")) {
            String[] spending = line.getOptionValues("s");
            if (spending.length != 2) {
                throw new InvalidFormatException();
            }
            s.symbol = spending[0];
            s.amount = Double.parseDouble(spending[1]);
        }
        return s;
    }

    protected static void addDescriptionOption(boolean required) {
        Option description = Option.builder("d")
            .longOpt("description")
            .hasArgs()
            .required(required)
            .argName("description")
            .build();

        options.addOption(description);
    }

    protected static String parseDescriptionOption(CommandLine line) {
        String[] descriptionArray = line.getOptionValues("d");
        if (descriptionArray == null) {
            return null;
        }
        return String.join(" ", descriptionArray);
    }

    static void addDateOption() {
        Option date = Option.builder("t")
            .hasArgs()
            .required()
            .build();

        options.addOption(date);
    }

    protected static String parseDateOption(CommandLine line) {
        String[] date = line.getOptionValues("t");
        return String.join(" ", date);
    }

    /**
     * getOptionalIndex parses line with flag for 0..1 argument given
     * @param line to check flags with
     * @param flag for command
     * @return null for option not selected, -1 for clear all, >=0 for clear 1
     * @throws InvalidCommandException if argument is given is invalid index
     */
    static Integer parseOptionalIndex(CommandLine line, String flag) throws InvalidCommandException, InvalidNumberException {
        if (!line.hasOption(flag)) {
            return null;
        }

        String indexString = line.getOptionValue(flag);
        boolean isClearAll = indexString == null;
        if (isClearAll) {
            return -1;
        }

        Integer index = getIndex(indexString);
        if (index == null) {
            throw new InvalidCommandException();
        }
        return index;
    }

    /**
     * Parse arguments into command line with no extra values outside of arguments
     * @param args space-separated arguments to parse
     * @return command line with options
     * @throws ParseException for errors parsing
     * @throws InvalidCommandException if values found outside of arguments
     */
    protected static CommandLine getCommandLine(String[] args) throws ParseException, InvalidCommandException {
        return getCommandLine(args, false);
    }

    protected static CommandLine getCommandLine(String[] args, boolean hasArgs) throws ParseException, InvalidCommandException {
        CommandLine line = parser.parse(options, args);
        if (!hasArgs) {
            parseNoArgs(line);
        }
        return line;
    }

    public abstract Command parse(String[] args) throws ParseException, InvalidCommandException,
            java.text.ParseException, IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException, InvalidFormatException, InvalidNumberException;

    public Parser() {
        parser = new DefaultParser();
        options = new Options();
    }

    protected static int getIndex(CommandLine line) throws InvalidFormatException, InvalidNumberException {
        String[] indexString = line.getArgs();
        if (indexString.length != 1) {
            throw new InvalidFormatException();
        }
        return getIndex(indexString[0]);
    }

    protected static Integer getIndex(String s) throws InvalidNumberException {
        if (s == null) {
            return null;
        }
        int index = Integer.parseInt(s);
        if (index <= 0) {
            throw new InvalidNumberException();
        }
        return index;
    }

    public static Command parseCommand(String userInput) throws InvalidCommandException, ParseException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            java.text.ParseException, InvalidFormatException, InvalidNumberException {
        String[] args = userInput.strip().split(" ");
        if (args.length == 0) {
            throw new InvalidCommandException();
        }

        String cmd = args[0];
        String[] opts = Arrays.copyOfRange(args, 1, args.length);

        switch (cmd) {
        case "add":
            return new AddParser().parse(opts);
        case "clear":
            return new ClearParser().parse(opts);
        case "convert":
            return new ConvertParser().parse(opts);
        case "draw":
            return new DateParser<>(DrawCommand.class).parse(opts);
        case "edit":
            return new EditParser().parse(opts);
        case "export":
            return new ExportCommand(String.join(" ", opts));
        case "help":
            return new HelpCommand();
        case "logout":
            return new ExitCommand();
        case "repay":
            return new RepayParser().parse(opts);
        case "purge":
            checkRemainingCommand(opts, "data");
            return new PurgeDataCommand();
        case "repayment":
            checkRemainingCommand(opts, "list");
            return new RepaymentListCommand();
        case "set":
            return new SetParser().parse(opts);
        case "spending":
            return new SpendingListParser().parse(opts);
        case "summary":
            return new DateParser<>(SummaryCommand.class).parse(opts);
        case "view":
            return new ViewBudgetCommand();
        default:
            throw new InvalidCommandException();
        }
    }

    private static void checkRemainingCommand(String[] args, String remainingCommand) throws InvalidCommandException {
        String[] c = remainingCommand.strip().split(" ");
        if (args.length != c.length || !Arrays.equals(c, args)) {
            throw new InvalidCommandException();
        }
    }
}
