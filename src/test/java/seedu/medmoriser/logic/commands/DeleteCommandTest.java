package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.logic.commands.CommandTestUtil.showQAndAAtIndex;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_FIRST_QANDA;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_SECOND_QANDA;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        QAndA qAndAToDelete = model.getFilteredQAndAList()
                .get(INDEX_FIRST_QANDA.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QANDA);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QANDA_SUCCESS, qAndAToDelete);

        ModelManager expectedModel = new ModelManager(model.getMedmoriser(), new UserPrefs());
        expectedModel.deleteQAndA(qAndAToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQAndAList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QANDA_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showQAndAAtIndex(model, INDEX_FIRST_QANDA);

        QAndA qAndAToDelete = model.getFilteredQAndAList()
                .get(INDEX_FIRST_QANDA.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QANDA);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QANDA_SUCCESS, qAndAToDelete);

        Model expectedModel = new ModelManager(model.getMedmoriser(), new UserPrefs());
        expectedModel.deleteQAndA(qAndAToDelete);
        showNoQAndA(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showQAndAAtIndex(model, INDEX_FIRST_QANDA);

        Index outOfBoundIndex = INDEX_SECOND_QANDA;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMedmoriser().getQAndAList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QANDA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_QANDA);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_QANDA);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_QANDA);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different qAndA -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoQAndA(Model model) {
        model.updateFilteredQAndAList(p -> false);

        assertTrue(model.getFilteredQAndAList().isEmpty());
    }
}
