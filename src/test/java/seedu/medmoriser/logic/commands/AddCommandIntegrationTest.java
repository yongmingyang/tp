package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalMedmoriser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.testutil.QuestionSetBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
    }

    @Test
    public void execute_newQuestionSet_success() {
        QAndA validQAndA = new QuestionSetBuilder().build();

        Model expectedModel = new ModelManager(model.getMedmoriser(), new UserPrefs());
        expectedModel.addQuestionSet(validQAndA);

        assertCommandSuccess(new AddCommand(validQAndA), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validQAndA), expectedModel);
    }

    @Test
    public void execute_duplicateQuestionSet_throwsCommandException() {
        QAndA qAndAInList = model.getMedmoriser().getQAndAList().get(0);
        assertCommandFailure(new AddCommand(qAndAInList), model, AddCommand.MESSAGE_DUPLICATE_QUESTIONSET);
    }

}
