package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;

/**
 * Receives the user's answer for a quiz.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": The answer that the user keys in for the quiz "
            + "question. \n"
            + "Example: " + COMMAND_WORD + " This is my answer";

    public static final String MESSAGE_USER_ANSWER = "Your answer: ";

    public static final String MESSAGE_NOT_QUIZ = "There is no ongoing quiz.";

    public static final String MESSAGE_ALREADY_ANSWERED = "This quiz has already been answered";

    private static boolean beenAnswered = false;

    private static CommandResult currCommandResult;

    private final String userAnswer;

    /**
     * Creates an AnswerCommand
     * @param userAnswer The user's input answer.
     */
    public AnswerCommand(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!QuizCommand.getIsQuiz()) {
            throw new CommandException(MESSAGE_NOT_QUIZ);
        } else {
            if (beenAnswered) {
                throw new CommandException(currCommandResult.getFeedbackToUser() + "\n" + MESSAGE_ALREADY_ANSWERED);
            } else {
                setBeenAnswered(true, model);
                model.getFilteredQAndAList().get(0).setQuizAnswer();
                currCommandResult = new CommandResult(MESSAGE_USER_ANSWER + userAnswer);
                return currCommandResult;
            }
        }
    }

    public static void setBeenAnswered(boolean hasBeenAnswered, Model model) {
        beenAnswered = hasBeenAnswered;
        if (!beenAnswered) {
            for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
                model.getFilteredQAndAList().get(i).setNotBeenAnswered();
            }
        } else {
            model.getFilteredQAndAList().get(0).setBeenAnswered();
        }
    }

    public static void setCurrCommandResult(String msg) {
        currCommandResult = new CommandResult(msg);
    }
}
