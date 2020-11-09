package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.QAndA;

public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_SUCCESS = "Here's the next question!";

    public static final String MESSAGE_NO_MORE_QUESTIONS =
            "There are no more question with this keyword! Enter `endquiz` to end the quiz.";

    public static final String MESSAGE_NOT_QUIZ = "There is no ongoing quiz.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (QuizCommand.getIsQuiz()) {
            if (QuizCommand.getCurrentList().size() > 0) {
                AnswerCommand.setBeenAnswered(false, model);
                QAndA qAndA = QuizCommand.getRandomQuestion(QuizCommand.getCurrentList());
                model.updateFilteredQAndAList(x -> x.equals(qAndA));
                QuizCommand.setIsQuiz(true, model);
                QuizCommand.getCurrentList().remove(qAndA);
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                throw new CommandException(MESSAGE_NO_MORE_QUESTIONS);
            }
        } else {
            throw new CommandException(MESSAGE_NOT_QUIZ);
        }

    }
}
