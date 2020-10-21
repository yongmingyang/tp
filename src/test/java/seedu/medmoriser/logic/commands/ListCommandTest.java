package seedu.medmoriser.logic.commands;

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
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(true), model, ListCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionSetAtIndex(model, INDEX_FIRST_QANDA);
        assertCommandSuccess(new ListCommand(true), model, ListCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }
}
