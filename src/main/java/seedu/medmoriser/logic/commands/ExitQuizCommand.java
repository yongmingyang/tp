package seedu.medmoriser.logic.commands;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;

/**
 * Exits an ongoing quiz
 */
public class ExitQuizCommand extends Command {

    public static final String COMMAND_WORD = "exitquiz";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting ongoing quiz as requested ...";

    public static final String MESSAGE_NO_ONGOING_QUIZ = "There is no ongoing quiz.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!QuizCommand.isQuiz) {
            throw new CommandException(MESSAGE_NO_ONGOING_QUIZ);
        } else {
            QuizCommand.isQuiz = false;
            return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        }
    }
}
