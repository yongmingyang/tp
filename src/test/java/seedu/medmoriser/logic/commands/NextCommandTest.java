package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.NextCommand.MESSAGE_NOT_QUIZ;
import static seedu.medmoriser.logic.commands.NextCommand.MESSAGE_NO_MORE_QUESTIONS;
import static seedu.medmoriser.logic.commands.NextCommand.MESSAGE_SUCCESS;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION3;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION5;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION6;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NextCommand.
 */
public class NextCommandTest {
    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
    private Model expectedModel = new ModelManager(new Medmoriser(model.getMedmoriser()), new UserPrefs());

    @Test
    public void equals() {
        NextCommand nextCommand = new NextCommand();

        // same object
        assertTrue(nextCommand.equals(nextCommand));

        // different types -> returns false
        assertFalse(nextCommand.equals(1));

        // null -> returns false
        assertFalse(nextCommand.equals(null));

    }

    @Test
    public void execute_noOngoingQuiz_failure() {
        String expectedMessage = MESSAGE_NOT_QUIZ;
        NextCommand command = new NextCommand();

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_noNextQuestion_failure() {
        String expectedMessage = MESSAGE_NO_MORE_QUESTIONS;
        NextCommand nextCommand = new NextCommand();

        QuestionContainsKeywordsPredicate predicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("Three"));
        QuizCommand command = new QuizCommand(predicate);
        expectedModel.updateFilteredQAndAList(predicate);

        ObservableList<QAndA> filteredList = expectedModel.getFilteredQAndAList();

        assertTrue(filteredList.size() == 1);
        assertEquals(Arrays.asList(QUESTION3), expectedModel.getFilteredQAndAList());

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        try {
            command.execute(model);
            nextCommand.execute(model);
            EndQuizCommand exitQuizCommand = new EndQuizCommand();
            exitQuizCommand.execute(model);
        } catch (CommandException e) {
            CommandResult result = new CommandResult(e.getMessage());
            assertEquals(expectedCommandResult, result );
        }
    }

    @Test
    public void execute_nextQuestion_success() {
        String expectedMessage = MESSAGE_SUCCESS;
        NextCommand nextCommand = new NextCommand();

        QuestionContainsKeywordsPredicate predicate = preparePredicate("three five six");
        String expectedQuizMessage = QuizCommand.MESSAGE_SUCCESS;
        QuizCommand quizCommand = new QuizCommand(predicate);
        expectedModel.updateFilteredQAndAList(predicate);

        ObservableList<QAndA> filteredList = expectedModel.getFilteredQAndAList();

        assertTrue(filteredList.size() == 3);
        assertEquals(Arrays.asList(QUESTION3, QUESTION5, QUESTION6), expectedModel.getFilteredQAndAList());

        EndQuizCommand endQuizCommand = new EndQuizCommand();
        String expectedEndQuizMessage = EndQuizCommand.MESSAGE_ENDQUIZ_ACKNOWLEDGEMENT;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        CommandResult expectedQuizCommandResult = new CommandResult(expectedQuizMessage);
        CommandResult expectedEndQuizCommandResult = new CommandResult(expectedEndQuizMessage);
        try {
            CommandResult result = quizCommand.execute(model);
            CommandResult resultNext = nextCommand.execute(model);
            CommandResult resultNextQuestion = nextCommand.execute(model);
            CommandResult endQuizResult = endQuizCommand.execute(model);

            assertEquals(expectedQuizCommandResult, result);
            assertEquals(expectedCommandResult, resultNext);
            assertEquals(expectedCommandResult, resultNextQuestion);
            assertEquals(expectedEndQuizCommandResult, endQuizResult);

        } catch (CommandException e) {
            throw new AssertionError("Command should not fail", e);
        }
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
