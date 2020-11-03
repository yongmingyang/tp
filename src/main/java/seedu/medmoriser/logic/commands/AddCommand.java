package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Adds a QuestionSet to the question bank.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a QAndA to the question bank "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "How many taste buds does the average human have?" + " "
            + PREFIX_ANSWER + "10,000" + " "
            + PREFIX_TAG + "Human Anatomy";

    public static final String MESSAGE_SUCCESS = "New QAndA added: %1$s";
    public static final String MESSAGE_DUPLICATE_QANDA = "This QAndA already exists in the answer book";

    private final QAndA toAdd;

    /**
     * Creates an AddCommand to add the specified {@code QuestionSet}
     */
    public AddCommand(QAndA qAndA) {
        requireNonNull(qAndA);
        toAdd = qAndA;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasQAndA(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_QANDA);
        } else if (QuizCommand.getIsQuiz()) {
            throw new CommandException(Messages.MESSAGE_ONGOING_QUIZ);
        } else {
            model.addQAndA(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
