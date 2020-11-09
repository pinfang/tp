package seedu.duke.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import seedu.duke.command.Command;
import seedu.duke.command.ConvertCommand;
import seedu.duke.exceptions.InvalidCommandException;

//@author k-walter
public class ConvertParser extends Parser {
    public ConvertParser() {
        super();
        Option source = Option.builder("s")
            .longOpt("source")
            .hasArg()
            .required()
            .argName("source")
            .build();
        Option target = Option.builder("t")
            .longOpt("target")
            .hasArg()
            .required()
            .argName("target")
            .build();

        options.addOption(source);
        options.addOption(target);
    }

    @Override
    public Command parse(String[] args) throws ParseException, InvalidCommandException {
        CommandLine line = getCommandLine(args);
        String source = line.getOptionValue("s");
        String target = line.getOptionValue("t");
        return new ConvertCommand(source, target);
    }

}
