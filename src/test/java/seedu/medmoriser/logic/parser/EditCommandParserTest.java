package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_TAG1;
import static seedu.medmoriser.logic.commands.CommandTestUtil.TAG_DESC_TAG2;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG1;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.medmoriser.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_FIRST_QANDA;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_SECOND_QANDA;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_THIRD_QUESTIONSET;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.logic.commands.EditCommand;
import seedu.medmoriser.logic.commands.EditCommand.EditQuestionSetDescriptor;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;
import seedu.medmoriser.testutil.EditQuestionSetDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS); // invalid ANSWER
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code QuestionSet} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_TAG1 + TAG_DESC_TAG2 + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_TAG1 + TAG_EMPTY + TAG_DESC_TAG2,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_TAG1 + TAG_DESC_TAG2,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC + VALID_ANSWER_A,
                Question.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_QANDA;
        String userInput = targetIndex.getOneBased() + TAG_DESC_TAG2 + ANSWER_DESC_A + QUESTION_DESC_A
                + TAG_DESC_TAG1;

        EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_A)
                .withAnswer(VALID_ANSWER_A).withTags(VALID_TAG_TAG2, VALID_TAG_TAG1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_QUESTIONSET;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_A;
        EditQuestionSetDescriptor descriptor =
                new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_A).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // answer
        userInput = targetIndex.getOneBased() + ANSWER_DESC_A;
        descriptor = new EditQuestionSetDescriptorBuilder().withAnswer(VALID_ANSWER_A).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_TAG1;
        descriptor = new EditQuestionSetDescriptorBuilder().withTags(VALID_TAG_TAG1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_QANDA;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_A
                + TAG_DESC_TAG1 + ANSWER_DESC_A + TAG_DESC_TAG1
                + ANSWER_DESC_B + TAG_DESC_TAG2;

        EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder().withAnswer(VALID_ANSWER_B)
                .withTags(VALID_TAG_TAG1, VALID_TAG_TAG2)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_QUESTIONSET;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
