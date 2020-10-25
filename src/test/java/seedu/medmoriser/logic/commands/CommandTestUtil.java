package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
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
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.testutil.EditQAndADescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_A = "Question A";
    public static final String VALID_QUESTION_B = "Question B";
    public static final String VALID_ANSWER_A = "Answer A";
    public static final String VALID_ANSWER_B = "Answer B";
    public static final String VALID_TAG_TAG1 = "tag1";
    public static final String VALID_TAG_TAG2 = "tag2";

    public static final String QUESTION_DESC_A = " " + PREFIX_QUESTION + VALID_QUESTION_A;
    public static final String QUESTION_DESC_B = " " + PREFIX_QUESTION + VALID_QUESTION_B;
    public static final String ANSWER_DESC_A = " " + PREFIX_ANSWER + VALID_ANSWER_A;
    public static final String ANSWER_DESC_B = " " + PREFIX_ANSWER + VALID_ANSWER_B;
    public static final String TAG_DESC_TAG1 = " " + PREFIX_TAG + VALID_TAG_TAG1;
    public static final String TAG_DESC_TAG2 = " " + PREFIX_TAG + VALID_TAG_TAG2;

    public static final String INVALID_QUESTION_DESC = " "
            + PREFIX_QUESTION + "Question&"; // '&' not allowed in questions
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "tag*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditQAndADescriptor DESC_A;
    public static final EditCommand.EditQAndADescriptor DESC_B;

    static {
        DESC_A = new EditQAndADescriptorBuilder().withQuestion(VALID_QUESTION_A)
                .withAnswer(VALID_ANSWER_A)
                .withTags(VALID_TAG_TAG1).build();
        DESC_B = new EditQAndADescriptorBuilder().withQuestion(VALID_QUESTION_B)
                .withAnswer(VALID_ANSWER_B)
                .withTags(VALID_TAG_TAG2, VALID_TAG_TAG1).build();
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
     * - the address book, filtered qAndA list and selected qAndA in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Medmoriser expectedMedmoriser = new Medmoriser(actualModel.getMedmoriser());
        List<QAndA> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQAndAList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedMedmoriser, actualModel.getMedmoriser());
        assertEquals(expectedFilteredList, actualModel.getFilteredQAndAList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the qAndA at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showQAndAAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQAndAList().size());

        QAndA qAndA = model.getFilteredQAndAList().get(targetIndex.getZeroBased());
        final String[] splitName = qAndA.getQuestion().question.split("\\s+");
        model.updateFilteredQAndAList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[1])));

        assertEquals(1, model.getFilteredQAndAList().size());
    }

}
