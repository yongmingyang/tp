package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.FindCommand;
import seedu.medmoriser.model.qanda.AnswerContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsQAndAContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new QAndAContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice, Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice, \n Bob ", expectedFindCommand);
    }

    @Test
    public void parse_validArgsAnswerContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnswerContainsKeywordsPredicate(Arrays.asList("a", "Alice", "Bob")));
        assertParseSuccess(parser, "a/Alice, Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "a/ \n Alice, \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsQuestionContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new QuestionContainsKeywordsPredicate(Arrays.asList("q", "Alice", "Bob")));
        assertParseSuccess(parser, "q/Alice, Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "q/ Alice,  Bob  ", expectedFindCommand);
    }

    @Test
    public void parse_validArgsTagContainsKeywordsPredicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("t", "Alice", "Bob")));
        assertParseSuccess(parser, "t/Alice, Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/ \n Alice, \n \t Bob  \t", expectedFindCommand);
    }

}
