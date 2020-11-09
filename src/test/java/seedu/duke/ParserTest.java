package seedu.duke;

import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import seedu.duke.command.AddCommand;
import seedu.duke.command.ClearRepaymentListCommand;
import seedu.duke.command.Command;
import seedu.duke.command.ConvertCommand;
import seedu.duke.command.DrawCommand;
import seedu.duke.command.EditCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ExportCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.MultipleCommand;
import seedu.duke.command.PurgeDataCommand;
import seedu.duke.command.RepayCommand;
import seedu.duke.command.RepaymentListCommand;
import seedu.duke.command.SetBudgetCommand;
import seedu.duke.command.SpendingListCommand;
import seedu.duke.command.SummaryCommand;
import seedu.duke.exceptions.InvalidCommandException;
import seedu.duke.exceptions.InvalidMonthException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.InvalidYearException;
import seedu.duke.parser.Parser;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    static class Rule<T extends Throwable> {
        final String commandString;
        final Class<T> subclass;

        Rule(String command, Class<T> subclass) {
            this.commandString = command;
            this.subclass = subclass;
        }
    }

    //@author k-walter
    @Test
    void allParserReturnClass() throws Exception {
        Rule[] rules = new Rule[]{
            new Rule("add -c Food -d Item 0 -s SGD 114.514", AddCommand.class),
            new Rule("add -d Chicken Rice 0 -s SGD 114.514 ", AddCommand.class),
            new Rule("edit 100 -s SGD 1.23 --description Chicken Rice -c Food", EditCommand.class),
            new Rule("edit 100 -s SGD 1.23 -c Food Stuff", EditCommand.class),
            new Rule("clear --spending 234", MultipleCommand.class),
            new Rule("clear -r 1 -b --spending 234", MultipleCommand.class),
            new Rule("clear -r 420", MultipleCommand.class),
            new Rule("convert -s SGD --target CNY", ConvertCommand.class),
            new Rule("draw ", DrawCommand.class),
            new Rule("draw 2020", DrawCommand.class),
            new Rule("export ./data", ExportCommand.class),
            new Rule("logout", ExitCommand.class),
            new Rule("purge data", PurgeDataCommand.class),
            new Rule("repay -s CAD 3.14 -t 2020-12-02 --description John", RepayCommand.class),
            new Rule("repayment list", RepaymentListCommand.class),
            new Rule("summary 2020 Jul", SummaryCommand.class),
            new Rule("summary --all", SummaryCommand.class),
            new Rule("spending list", SpendingListCommand.class),
            new Rule("spending list 2020", SpendingListCommand.class),
            new Rule("set --spending SGD 123.45", SetBudgetCommand.class),
            new Rule("help", HelpCommand.class)
        };
        for (Rule r : rules) {
            Command c;
            try {
                c = Parser.parseCommand(r.commandString);
            } catch (Exception e) {
                throw new Exception("Command: " + r.commandString, e);
            }
            assertTrue(r.subclass.isInstance(c), String.format("Expected %s, got %s", r.subclass, c.getClass()));
        }
    }

    //@author k-walter
    @Test
    void exactNumberOfArgs() {
        Rule[] invalidCommands = new Rule[]{
            new Rule("add extra -c Food -d Item 0 -s SGD 114.514", InvalidCommandException.class),
            new Rule("clear -b extra -s 1 -r 2", InvalidCommandException.class),
            new Rule("clear -s 1 2 -r 2", InvalidCommandException.class),
            new Rule("convert -s SGD extra -t USD", InvalidCommandException.class),
            new Rule("edit 100 200 -s SGD 1.23 --description Chicken Rice -c Food", InvalidCommandException.class),
            new Rule("draw 2020 Jun 21", InvalidCommandException.class),
            new Rule("draw incorrectText", InvalidYearException.class),
            new Rule("draw --all extra", InvalidCommandException.class),
            new Rule("logout extra", InvalidCommandException.class),
            new Rule("purge data text", InvalidCommandException.class),
            new Rule("purge", InvalidCommandException.class),
            new Rule("purge incorrectText", InvalidCommandException.class),
            new Rule("purge data extra", InvalidCommandException.class),
            new Rule("repay -s CAD 3.14 -t 2020-12-02 0100 --description John", InvalidCommandException.class),
            new Rule("repayment", InvalidCommandException.class),
            new Rule("repayment incorrectText", InvalidCommandException.class),
            new Rule("repayment list extra", InvalidCommandException.class),
            new Rule("summary incorrectText", InvalidYearException.class),
            new Rule("summary 2020 Jul 21", InvalidCommandException.class),
            new Rule("summary --all extra", InvalidCommandException.class),
            new Rule("spending", InvalidCommandException.class),
            new Rule("spending incorrectText", InvalidCommandException.class),
            new Rule("spending list extra", InvalidYearException.class),
            new Rule("spending list 2020 Jun 21", InvalidCommandException.class),
            new Rule("spending list --all extra", InvalidCommandException.class),
            new Rule("set --spending SGD 123.45 0", InvalidCommandException.class),
            new Rule("set 0 -s SGD 1.23", InvalidCommandException.class),
            new Rule("help me", InvalidCommandException.class),
        };
        for (Rule r : invalidCommands) {
            assertThrows(r.subclass, () -> Parser.parseCommand(r.commandString), r.commandString);
        }
    }

    //@author k-walter
    @Test
    void addWithoutCategory() throws ParseException, InvalidCommandException, NoSuchMethodException,
        InstantiationException, IllegalAccessException, InvocationTargetException,
        java.text.ParseException, InvalidNumberException, InvalidYearException,
        InvalidMonthException {
        AddCommand c = (AddCommand) Parser.parseCommand("add -d Item 0 -s SGD 114.514 ");
        assertEquals(c.amount, 114.514);
        assertEquals(c.description, "Item 0");
        assertNull(c.category);
    }

    @Test
    void addShuffledArg() throws ParseException, InvalidCommandException, NoSuchMethodException,
        InstantiationException, IllegalAccessException, InvocationTargetException,
        java.text.ParseException, InvalidNumberException, InvalidYearException,
        InvalidMonthException {
        AddCommand c = (AddCommand) Parser.parseCommand("add -d Item 0 -s SGD 114.514 -c Food ");
        assertEquals(c.amount, 114.514);
        assertEquals(c.description, "Item 0");
        assertEquals(c.category, "Food");
    }

    //@author k-walter
    @Test
    void editOneParam() throws ParseException, InvalidCommandException, NoSuchMethodException, InstantiationException,
        IllegalAccessException, InvocationTargetException, java.text.ParseException,
        InvalidNumberException, InvalidYearException, InvalidMonthException {
        EditCommand c = (EditCommand) Parser.parseCommand("edit 987 --description fried rice");
        assertEquals(c.index, 986);
        assertEquals(c.description, "fried rice");
        assertNull(c.amount);
        assertNull(c.currency);
        assertNull(c.category);
    }

    //@author k-walter
    @Test
    void clearIndex() throws ParseException, InvalidCommandException, NoSuchMethodException,
        InstantiationException, IllegalAccessException, java.text.ParseException,
        InvocationTargetException, InvalidNumberException, InvalidYearException,
        InvalidMonthException {
        MultipleCommand c = (MultipleCommand) Parser.parseCommand("clear --repayment 23");
        ClearRepaymentListCommand cl = (ClearRepaymentListCommand) c.commands.get(0);
        assertFalse(cl.isClearAll);
        assertEquals(cl.clearIndex, 23);
    }

    //@author k-walter
    @Test
    void clearMultipleLists() throws NoSuchMethodException, ParseException, InvalidCommandException,
        InstantiationException, java.text.ParseException, IllegalAccessException,
        InvocationTargetException, InvalidNumberException, InvalidYearException,
        InvalidMonthException {
        MultipleCommand c = (MultipleCommand) Parser.parseCommand("clear --spending 234");
        assertEquals(1, c.commands.size());
        c = (MultipleCommand) Parser.parseCommand("clear -r 1 --spending 234");
        assertEquals(2, c.commands.size());
        c = (MultipleCommand) Parser.parseCommand("clear -r 1 -b --spending 234");
        assertEquals(3, c.commands.size());
    }

    //@author k-walter
    @Test
    void convertMissingSource() {
        assertThrows(MissingOptionException.class, () -> Parser.parseCommand("convert --target USD"));
    }
}