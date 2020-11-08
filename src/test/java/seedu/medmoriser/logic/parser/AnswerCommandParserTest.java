package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.AnswerCommand;

public class AnswerCommandParserTest {

    private static final String USER_INPUT_1 = "Some answer";
    private static final String EMPTY_USER_INPUT = "";

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_validArgs_returnsAnswerCommand() {
        assertParseSuccess(parser, USER_INPUT_1, new AnswerCommand(USER_INPUT_1));
    }

    @Test
    public void parse_emptyAnswer_throwsParseException() {
        assertParseFailure(parser, EMPTY_USER_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
    }
}
