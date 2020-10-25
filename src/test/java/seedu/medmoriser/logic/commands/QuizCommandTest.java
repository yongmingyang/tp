package seedu.medmoriser.logic.commands;


import java.util.Arrays;
import java.util.Collections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.medmoriser.logic.commands.QuizCommand.MESSAGE_NO_QUESTION_WITH_KEYWORD;
import static seedu.medmoriser.logic.commands.QuizCommand.MESSAGE_SUCCESS;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

public class QuizCommandTest {
    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void equals() {
        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("second"));

        TagContainsKeywordsPredicate thirdPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate fourthPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));


        QuizCommand quizFirstCommand = new QuizCommand(firstPredicate);
        QuizCommand quizSecondCommand = new QuizCommand(secondPredicate);
        QuizCommand quizThirdCommand = new QuizCommand(thirdPredicate);
        QuizCommand quizFourthCommand = new QuizCommand(fourthPredicate);

        // same object -> returns true
        assertTrue(quizFirstCommand.equals(quizFirstCommand));
        assertTrue(quizThirdCommand.equals(quizThirdCommand));

        // same values -> returns true
        QuizCommand quizFirstCommandCopy = new QuizCommand(firstPredicate);
        assertTrue(quizFirstCommand.equals(quizFirstCommandCopy));
        QuizCommand quizThirdCommandCopy = new QuizCommand(thirdPredicate);
        assertTrue(quizThirdCommand.equals(quizThirdCommandCopy));

        // different types -> returns false
        assertFalse(quizFirstCommand.equals(1));

        // null -> returns false
        assertFalse(quizFirstCommand.equals(null));

        // different qAndA -> returns false
        assertFalse(quizFirstCommand.equals(quizSecondCommand));
        assertFalse(quizThirdCommand.equals(quizFourthCommand));
    }

    @Test
    public void execute_zeroKeywords_throwsCommandException() {
        String expectedMessage = MESSAGE_NO_QUESTION_WITH_KEYWORD;
        QuestionContainsKeywordsPredicate predicate = preparePredicate(" ");
        QuizCommand command = new QuizCommand(predicate);
        model.updateFilteredQAndAList(predicate);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_multipleKeywords_multipleQAndAsFound() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, 3);
        QuestionContainsKeywordsPredicate predicate = preparePredicate("three five six");
        QuizCommand command = new QuizCommand(predicate);
        expectedModel.updateFilteredQAndAList(predicate);

        ObservableList<QAndA> filteredList = expectedModel.getFilteredQAndAList();
        QAndA filteredQAndA = filteredList.get(0);
        String filteredQuestionString = filteredQAndA.getQuestion().question;
        filteredQuestionString = filteredQuestionString.toLowerCase();
        
        assertTrue(filteredQuestionString.contains("three") ||
                filteredQuestionString.contains("five") ||
                filteredQuestionString.contains("six"));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        try {
            CommandResult result = command.execute(model);
            assertEquals(expectedCommandResult, result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
