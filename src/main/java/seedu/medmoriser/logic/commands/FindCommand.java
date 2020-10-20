package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.AnswerContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

import java.util.function.Predicate;

/**
 * Finds and lists all questionSets in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questionSets whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

//    private final QuestionContainsKeywordsPredicate predicate;

    private final Predicate<QAndA> predicate;

    public FindCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public FindCommand(AnswerContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredQAndAList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_QANDA_LISTED_OVERVIEW,
                        model.getFilteredQAndAList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
