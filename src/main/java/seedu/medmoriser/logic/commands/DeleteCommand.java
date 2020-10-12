package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Deletes a questionSet identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the questionSet identified by the index number used in the displayed questionSet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_QUESTIONSET_SUCCESS = "Deleted questionSet: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<QAndA> lastShownList = model.getFilteredQuestionSetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX);
        }

        QAndA qAndAToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteQuestionSet(qAndAToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_QUESTIONSET_SUCCESS, qAndAToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
