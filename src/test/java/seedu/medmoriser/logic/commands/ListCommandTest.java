package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.logic.commands.CommandTestUtil.showQuestionSetAtIndex;
import static seedu.medmoriser.testutil.TypicalIndexes.INDEX_FIRST_QANDA;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalMedmoriser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUpModels() {
        model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
        expectedModel = new ModelManager(model.getMedmoriser(), new UserPrefs());
    }

    @Test
    public void equals() {

        ListCommand listAllCommand = new ListCommand(true);
        ListCommand listQuestionsCommand = new ListCommand(false);

        // same object -> returns true
        assertTrue(listAllCommand.equals(listAllCommand));

        // same values -> returns true
        ListCommand listAllCommandCopy = new ListCommand(true);
        assertTrue(listAllCommand.equals(listAllCommandCopy));

        // different types -> returns false
        assertFalse(listAllCommand.equals(1));

        // null -> returns false
        assertFalse(listAllCommand.equals(null));

        // different value for isAnswerDisplayed -> returns false
        assertFalse(listAllCommand.equals(listQuestionsCommand));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        // questions and answers both shown
        assertCommandSuccess(new ListCommand(true), model, ListCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);

        // questions shown, answers hidden
        assertCommandSuccess(new ListCommand(false), model, ListCommand.MESSAGE_LIST_QUESTIONS_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QANDA);
        assertCommandSuccess(new ListCommand(true), model, ListCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

}
