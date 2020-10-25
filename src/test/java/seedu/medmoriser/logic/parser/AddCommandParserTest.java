package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_TAG1;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_TAG2;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG1;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTIONA;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTIONB;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.AddCommand;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        QAndA expectedQAndA = new QuestionSetBuilder(QUESTIONB).withTags(VALID_TAG_TAG1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_B
                + ANSWER_DESC_B + TAG_DESC_TAG1, new AddCommand(expectedQAndA));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_A + QUESTION_DESC_B
                + ANSWER_DESC_B + TAG_DESC_TAG1, new AddCommand(expectedQAndA));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, QUESTION_DESC_B + ANSWER_DESC_B + TAG_DESC_TAG1,
                new AddCommand(expectedQAndA));

        // multiple emails - last email accepted
        assertParseSuccess(parser, QUESTION_DESC_B + ANSWER_DESC_B + TAG_DESC_TAG1,
                new AddCommand(expectedQAndA));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_B + ANSWER_DESC_A
                + ANSWER_DESC_B + TAG_DESC_TAG1, new AddCommand(expectedQAndA));

        // multiple tags - all accepted
        QAndA expectedQAndAMultipleTags = new QuestionSetBuilder(QUESTIONB).withTags(VALID_TAG_TAG1,
                VALID_TAG_TAG2)
                .build();
        assertParseSuccess(parser, QUESTION_DESC_B + ANSWER_DESC_B
                + TAG_DESC_TAG1 + TAG_DESC_TAG2, new AddCommand(expectedQAndAMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        QAndA expectedQAndA = new QuestionSetBuilder(QUESTIONA).withTags().build();
        assertParseSuccess(parser, QUESTION_DESC_A + ANSWER_DESC_A,
                new AddCommand(expectedQAndA));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_QUESTION_B + ANSWER_DESC_B, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_B + VALID_ANSWER_B, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_B + VALID_ANSWER_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_B
                + TAG_DESC_TAG1 + TAG_DESC_TAG2, Question.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_B + INVALID_ANSWER_DESC
                + TAG_DESC_TAG1 + TAG_DESC_TAG2, Answer.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, QUESTION_DESC_B + ANSWER_DESC_B
                + INVALID_TAG_DESC + VALID_TAG_TAG1, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + INVALID_ANSWER_DESC,
                Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_B
                + ANSWER_DESC_B + TAG_DESC_TAG1 + TAG_DESC_TAG2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
