package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QANDA;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;

/**
 * Lists all QAndAs in Medmoriser to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all Questions (and Answers) "
            + "depending on the optional parameter passed.\n"
            + "Parameters: [questions]\n"
            + "Example: " + COMMAND_WORD + " questions";

    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all QAndAs";

    public static final String MESSAGE_LIST_QUESTIONS_SUCCESS = "Listed all Questions";

    private final boolean isAnswerDisplayed;

    /**
     * Lists all QAndAs.
     * @param isAnswerDisplayed whether answers should be displayed
     */
    public ListCommand(boolean isAnswerDisplayed) {
        super();
        this.isAnswerDisplayed = isAnswerDisplayed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (int i = 0; i < model.getFilteredQAndAList().size(); i++) {
            model.getFilteredQAndAList().get(i).setNotQuiz();
        }

        if (QuizCommand.getIsQuiz()) {
            throw new CommandException(Messages.MESSAGE_ONGOING_QUIZ);
        } else {
            model.updateFilteredQAndAList(PREDICATE_SHOW_ALL_QANDA);
            if (isAnswerDisplayed) {
                return new CommandResult(MESSAGE_LIST_ALL_SUCCESS, true);
            } else {
                return new CommandResult(MESSAGE_LIST_QUESTIONS_SUCCESS, false);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && isAnswerDisplayed == (((ListCommand) other).isAnswerDisplayed)); // state check
    }
}
