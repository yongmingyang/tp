package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.logic.commands.AnswerCommand.MESSAGE_ALREADY_ANSWERED;
import static seedu.medmoriser.logic.commands.AnswerCommand.MESSAGE_NOT_QUIZ;
//import static seedu.medmoriser.logic.commands.AnswerCommand.MESSAGE_USER_ANSWER;
import static seedu.medmoriser.logic.commands.AnswerCommand.setCurrCommandResult;
import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;
//import seedu.medmoriser.model.qanda.QAndA;
//import seedu.medmoriser.testutil.TypicalQAndA;

public class AnswerCommandTest {

    public static final String USER_ANSWER_1 = "Some answer";
    public static final String USER_ANSWER_2 = "Another answer";

    private Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());

    @Test
    public void execute_answerNoOngoingQuiz_failure() {
        QuizCommand.setIsQuiz(false, model);

        // because model not supposed to change after answer command execution
        Model expectedModel = model;

        AnswerCommand answerCommand = new AnswerCommand(USER_ANSWER_1);
        assertCommandFailure(answerCommand, model, MESSAGE_NOT_QUIZ);
    }

    @Test
    public void execute_answerOngoingQuizHasBeenAnswered_failure() {
        QuizCommand.setIsQuiz(true, model);
        setCurrCommandResult(USER_ANSWER_2);
        AnswerCommand answerCommand = new AnswerCommand(USER_ANSWER_1);

        AnswerCommand.setBeenAnswered(true, model);
        model.getFilteredQAndAList().get(0).setQuizAnswer();

        String repeatedAnswerMessage = USER_ANSWER_2 + "\n" + MESSAGE_ALREADY_ANSWERED;
        assertCommandFailure(answerCommand, model, repeatedAnswerMessage);
    }
}
