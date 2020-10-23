package seedu.medmoriser.logic.commands;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;

import static java.util.Objects.requireNonNull;

public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": The answer that the user keys in for the quiz "
            + "question. \n"
            + "Example: " + COMMAND_WORD + "This is my answer";

    public static final String MESSAGE_USER_ANSWER = "Your answer: ";

    public static final String MESSAGE_NOT_QUIZ = "There is no ongoing quiz";

    private final String userAnswer;

    public AnswerCommand(String answer){
        this.userAnswer = answer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!QuizCommand.isQuiz) {
            throw new CommandException(MESSAGE_NOT_QUIZ);
        } else {
            QuizCommand.isQuiz = false;
            return new CommandResult(MESSAGE_USER_ANSWER + userAnswer);
        }
    }
}
