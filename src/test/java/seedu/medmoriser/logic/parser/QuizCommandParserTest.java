package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

public class QuizCommandParserTest {

    private QuizCommandParser parser = new QuizCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                QuizCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsQuestionContainsKeywordsPredicate_returnsQuizCommand() {
        // no leading and trailing whitespaces
        QuizCommand expectedQuizCommand =
                new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList("digestive system",
                        "skeletal system")));
        assertParseSuccess(parser, "q/digestive system, skeletal system", expectedQuizCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "q/ digestive system,  skeletal system  ", expectedQuizCommand);
    }

    @Test
    public void parse_validArgsTagContainsKeywordsPredicate_returnsQuizCommand() {
        // no leading and trailing whitespaces
        QuizCommand expectedQuizCommand =
                new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList("digestive system",
                        "skeletal system")));
        assertParseSuccess(parser, "t/digestive system, skeletal system", expectedQuizCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/ \n digestive system, \n \t skeletal system  \t", expectedQuizCommand);
    }

    @Test
    public void parse_invalidArgsMultiplePrefixes_throwsParseException() {
        String userInput = "q/digestive system t/Human Anatomy";
        String userInput2 = "q/digestive system t/Human Anatomy a/answer";

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                QuizCommand.MESSAGE_USAGE));

        assertParseFailure(parser, userInput2, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                QuizCommand.MESSAGE_USAGE));
    }
}
