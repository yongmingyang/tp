package seedu.medmoriser.logic.parser;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class QuizCommandParserTest {

    private QuizCommandParser parser = new QuizCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsQAndAContainsKeywordsPredicate_returnsQuizCommand() {
        // no leading and trailing whitespaces
        QuizCommand expectedQuizCommand =
                new QuizCommand(new QAndAContainsKeywordsPredicate(Arrays.asList("digestive", "skeletal")));
        System.out.println(expectedQuizCommand);
        assertParseSuccess(parser, "digestive, skeletal", expectedQuizCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n digestive, \n skeletal ", expectedQuizCommand);
    }

    @Test
    public void parse_validArgsQuestionContainsKeywordsPredicate_returnsQuizCommand() {
        // no leading and trailing whitespaces
        QuizCommand expectedQuizCommand =
                new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList("q", "digestive system", "skeletal system")));
        assertParseSuccess(parser, "q/digestive system, skeletal system", expectedQuizCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "q/ digestive system,  skeletal system  ", expectedQuizCommand);
    }

    @Test
    public void parse_validArgsTagContainsKeywordsPredicate_returnsQuizCommand() {
        // no leading and trailing whitespaces
        QuizCommand expectedQuizCommand =
                new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList("t", "digestive system", "skeletal system")));
        assertParseSuccess(parser, "t/digestive system, skeletal system", expectedQuizCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/ \n digestive system, \n \t skeletal system  \t", expectedQuizCommand);
    }
}
