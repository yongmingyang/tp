package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.medmoriser.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.questionset.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.testutil.EditQuestionSetDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_AMY = "Amy Bee";
    public static final String VALID_QUESTION_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ANSWER_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ANSWER_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String QUESTION_DESC_AMY = " " + PREFIX_QUESTION + VALID_QUESTION_AMY;
    public static final String QUESTION_DESC_BOB = " " + PREFIX_QUESTION + VALID_QUESTION_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ANSWER_DESC_AMY = " " + PREFIX_ANSWER + VALID_ANSWER_AMY;
    public static final String ANSWER_DESC_BOB = " " + PREFIX_ANSWER + VALID_ANSWER_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + "James&"; // '&' not allowed in questions
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditQuestionSetDescriptor DESC_AMY;
    public static final EditCommand.EditQuestionSetDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAnswer(VALID_ANSWER_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered questionSet list and selected questionSet in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Medmoriser expectedMedmoriser = new Medmoriser(actualModel.getAddressBook());
        List<QuestionSet> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQuestionSetList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedMedmoriser, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredQuestionSetList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the questionSet at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showQuestionSetAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionSetList().size());

        QuestionSet questionSet = model.getFilteredQuestionSetList().get(targetIndex.getZeroBased());
        final String[] splitName = questionSet.getQuestion().question.split("\\s+");
        model.updateFilteredQuestionSetList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredQuestionSetList().size());
    }

}
