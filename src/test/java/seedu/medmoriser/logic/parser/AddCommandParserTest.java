package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.medmoriser.testutil.TypicalQAndA.AMY;
import static seedu.medmoriser.testutil.TypicalQAndA.BOB;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.AddCommand;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Email;
import seedu.medmoriser.model.qanda.Phone;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;
import seedu.medmoriser.testutil.QAndABuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        QAndA expectedQAndA = new QAndABuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ANSWER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedQAndA));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_AMY + QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ANSWER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedQAndA));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ANSWER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedQAndA));

        // multiple emails - last email accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ANSWER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedQAndA));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ANSWER_DESC_AMY
                + ANSWER_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedQAndA));

        // multiple tags - all accepted
        QAndA expectedQAndAMultipleTags = new QAndABuilder(BOB).withTags(VALID_TAG_FRIEND,
                VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ANSWER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedQAndAMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        QAndA expectedQAndA = new QAndABuilder(AMY).withTags().build();
        assertParseSuccess(parser, QUESTION_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ANSWER_DESC_AMY,
                new AddCommand(expectedQAndA));
    }

    /*
    modified: phone and email no longer required
     */
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_QUESTION_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ANSWER_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        //assertParseFailure(parser, QUESTION_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ANSWER_DESC_BOB,
        //        expectedMessage);

        // missing email prefix
        //assertParseFailure(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ANSWER_DESC_BOB,
        //        expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ANSWER_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ANSWER_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ANSWER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Question.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, QUESTION_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ANSWER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ANSWER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ANSWER_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Answer.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ANSWER_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ANSWER_DESC,
                Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ANSWER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
