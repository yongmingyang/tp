package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

public class EndQuizCommandTest {
    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void execute_endOngoingQuiz_success() {
        QuestionContainsKeywordsPredicate predicate = preparePredicate("three");
        QuizCommand quizCommand = new QuizCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(EndQuizCommand.MESSAGE_ENDQUIZ_ACKNOWLEDGEMENT,
                false, false);

        try {
            quizCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertCommandSuccess(new EndQuizCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noOngoingQuizEndQuiz_throwsCommandException() {
        String expectedMessage = EndQuizCommand.MESSAGE_NO_ONGOING_QUIZ;
        EndQuizCommand command = new EndQuizCommand();
        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
