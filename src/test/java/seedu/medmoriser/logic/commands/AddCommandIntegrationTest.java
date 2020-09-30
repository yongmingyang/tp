package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.testutil.QuestionSetBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newQuestionSet_success() {
        QuestionSet validQuestionSet = new QuestionSetBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addQuestionSet(validQuestionSet);

        assertCommandSuccess(new AddCommand(validQuestionSet), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validQuestionSet), expectedModel);
    }

    @Test
    public void execute_duplicateQuestionSet_throwsCommandException() {
        QuestionSet questionSetInList = model.getAddressBook().getQuestionSetList().get(0);
        assertCommandFailure(new AddCommand(questionSetInList), model, AddCommand.MESSAGE_DUPLICATE_QUESTIONSET);
    }

}
