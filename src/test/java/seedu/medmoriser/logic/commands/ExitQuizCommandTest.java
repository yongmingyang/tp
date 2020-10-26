package seedu.medmoriser.logic.commands;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

public class ExitQuizCommandTest {
    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void execute_exitOngoingQuiz_success() {
        QuestionContainsKeywordsPredicate predicate = preparePredicate("three");
        QuizCommand quizCommand = new QuizCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(ExitQuizCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false);

        try {
            quizCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertCommandSuccess(new ExitQuizCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noOngoingQuizExitQuiz_throwsCommandException() {
        String expectedMessage = ExitQuizCommand.MESSAGE_NO_ONGOING_QUIZ;
        ExitQuizCommand command = new ExitQuizCommand();
        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
