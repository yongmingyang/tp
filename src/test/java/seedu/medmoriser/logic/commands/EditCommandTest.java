package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.DESC_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.logic.commands.CommandTestUtil.showQuestionSetAtIndex;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_FIRST_QANDA;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_SECOND_QANDA;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalMedmoriser;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.logic.commands.EditCommand.EditQuestionSetDescriptor;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.testutil.EditQuestionSetDescriptorBuilder;
import seedu.medmoriser.testutil.QuestionSetBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        QAndA editedQAndA = new QuestionSetBuilder().build();
        EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder(editedQAndA).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QANDA, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTIONSET_SUCCESS, editedQAndA);

        Model expectedModel = new ModelManager(new Medmoriser(model.getMedmoriser()), new UserPrefs());
        expectedModel.setQuestionSet(model.getFilteredQAndAList().get(0), editedQAndA);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastQuestionSet = Index.fromOneBased(model.getFilteredQAndAList().size());
        QAndA lastQAndA = model.getFilteredQAndAList().get(indexLastQuestionSet.getZeroBased());

        QuestionSetBuilder questionSetInList = new QuestionSetBuilder(lastQAndA);
        QAndA editedQAndA = questionSetInList.withQuestion(VALID_QUESTION_B).withTags(VALID_TAG_TAG2).build();

        EditCommand.EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder()
                .withQuestion(VALID_QUESTION_B).withTags(VALID_TAG_TAG2).build();
        EditCommand editCommand = new EditCommand(indexLastQuestionSet, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTIONSET_SUCCESS, editedQAndA);

        Model expectedModel = new ModelManager(new Medmoriser(model.getMedmoriser()), new UserPrefs());
        expectedModel.setQuestionSet(lastQAndA, editedQAndA);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QANDA, new EditQuestionSetDescriptor());
        QAndA editedQAndA = model.getFilteredQAndAList().get(INDEX_FIRST_QANDA.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTIONSET_SUCCESS, editedQAndA);

        Model expectedModel = new ModelManager(new Medmoriser(model.getMedmoriser()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QANDA);

        QAndA qAndAInFilteredList = model.getFilteredQAndAList()
                .get(INDEX_FIRST_QANDA.getZeroBased());
        QAndA editedQAndA = new QuestionSetBuilder(qAndAInFilteredList)
                .withQuestion(VALID_QUESTION_B).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QANDA,
                new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_B).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTIONSET_SUCCESS, editedQAndA);

        Model expectedModel = new ModelManager(new Medmoriser(model.getMedmoriser()), new UserPrefs());
        expectedModel.setQuestionSet(model.getFilteredQAndAList().get(0), editedQAndA);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateQuestionSetUnfilteredList_failure() {
        QAndA firstQAndA = model.getFilteredQAndAList().get(INDEX_FIRST_QANDA.getZeroBased());
        EditQuestionSetDescriptor descriptor = new EditQuestionSetDescriptorBuilder(firstQAndA).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_QANDA, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTIONSET);
    }

    @Test
    public void execute_duplicateQuestionSetFilteredList_failure() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QANDA);

        // edit questionSet in filtered list into a duplicate in medmoriser
        QAndA qAndAInList = model.getMedmoriser().getQAndAList()
                .get(INDEX_SECOND_QANDA.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QANDA,
                new EditQuestionSetDescriptorBuilder(qAndAInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTIONSET);
    }

    @Test
    public void execute_invalidQuestionSetIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQAndAList().size() + 1);
        EditQuestionSetDescriptor descriptor =
                new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_B).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QANDA_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of medmoriser
     */
    @Test
    public void execute_invalidQuestionSetIndexFilteredList_failure() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QANDA);
        Index outOfBoundIndex = INDEX_SECOND_QANDA;
        // ensures that outOfBoundIndex is still in bounds of medmoriser list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMedmoriser().getQAndAList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditQuestionSetDescriptorBuilder().withQuestion(VALID_QUESTION_B).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QANDA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_QANDA, DESC_A);

        // same values -> returns true
        EditQuestionSetDescriptor copyDescriptor = new EditQuestionSetDescriptor(DESC_A);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_QANDA, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_QANDA, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_QANDA, DESC_B)));
    }

}
