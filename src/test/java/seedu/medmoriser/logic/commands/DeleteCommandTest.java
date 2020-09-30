package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.logic.commands.CommandTestUtil.showQuestionSetAtIndex;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_FIRST_QUESTIONSET;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_SECOND_QUESTIONSET;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        QuestionSet questionSetToDelete = model.getFilteredQuestionSetList()
                .get(INDEX_FIRST_QUESTIONSET.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QUESTIONSET);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUESTIONSET_SUCCESS, questionSetToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteQuestionSet(questionSetToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionSetList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QUESTIONSET);

        QuestionSet questionSetToDelete = model.getFilteredQuestionSetList()
                .get(INDEX_FIRST_QUESTIONSET.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QUESTIONSET);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUESTIONSET_SUCCESS, questionSetToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteQuestionSet(questionSetToDelete);
        showNoQuestionSet(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QUESTIONSET);

        Index outOfBoundIndex = INDEX_SECOND_QUESTIONSET;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getQuestionSetList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_QUESTIONSET);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_QUESTIONSET);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_QUESTIONSET);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different questionSet -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoQuestionSet(Model model) {
        model.updateFilteredQuestionSetList(p -> false);

        assertTrue(model.getFilteredQuestionSetList().isEmpty());
    }
}
