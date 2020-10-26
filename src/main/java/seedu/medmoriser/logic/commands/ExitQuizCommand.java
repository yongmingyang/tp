package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QANDA;

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
        if (!QuizCommand.getIsQuiz()) {
            throw new CommandException(MESSAGE_NO_ONGOING_QUIZ);
        } else {
            QuizCommand.setIsQuiz(false);
            model.updateFilteredQAndAList(PREDICATE_SHOW_ALL_QANDA);
            for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
                model.getFilteredQAndAList().get(i).setNotQuiz();
            }
            return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        }
    }


}
