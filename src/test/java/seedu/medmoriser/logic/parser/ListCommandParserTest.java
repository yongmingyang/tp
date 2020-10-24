package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(true);

        // no trailing whitespace
        assertParseSuccess(parser, "", expectedListCommand);

        // trailing whitespace
        assertParseSuccess(parser, "   \t \n", expectedListCommand);
    }

    @Test
    public void parse_validArg_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand(false);
        assertParseSuccess(parser, "questions", expectedListCommand);

        // leading and trailing whitespace
        assertParseSuccess(parser, "\n questions \t", expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // missing 's' from parameter
        assertParseFailure(parser, "question",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        // >1 parameter
        assertParseFailure(parser, "all questions",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
