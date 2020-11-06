package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QANDA;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;

/**
 * Ends an ongoing quiz
 */
public class EndQuizCommand extends Command {

    public static final String COMMAND_WORD = "endquiz";

    public static final String MESSAGE_ENDQUIZ_ACKNOWLEDGEMENT = "Ending ongoing quiz as requested ...";

    public static final String MESSAGE_NO_ONGOING_QUIZ = "There is no ongoing quiz.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!QuizCommand.getIsQuiz()) {
            throw new CommandException(MESSAGE_NO_ONGOING_QUIZ);
        } else {
            QuizCommand.setIsQuiz(false, model);
            AnswerCommand.setBeenAnswered(false, model);
            model.updateFilteredQAndAList(PREDICATE_SHOW_ALL_QANDA);
            for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
                model.getFilteredQAndAList().get(i).setNotQuiz();
            }
            return new CommandResult(MESSAGE_ENDQUIZ_ACKNOWLEDGEMENT);
        }
    }


}
