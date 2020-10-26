package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.medmoriser.logic.commands.FindCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.AnswerContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        assert args != null;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywordsArray = trimmedArgs.split("/|, ");
        String[] excludeType = keywordsArray;

        //trim whitespaces
        keywordsArray = trimArg(keywordsArray);

        String findType = keywordsArray[0];
        if (keywordsArray.length > 1) {
            excludeType = Arrays.copyOfRange(keywordsArray, 1, keywordsArray.length);
        }
        switch (findType) {
        case "t":
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(excludeType)));
        case "q":
            return new FindCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(excludeType)));
        case "a":
            return new FindCommand(new AnswerContainsKeywordsPredicate(Arrays.asList(excludeType)));
        default:
            return new FindCommand(new QAndAContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
        }
    }

    private String[] trimArg(String[] args) {
        String[] toReturn = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            toReturn[i] = args[i].trim();
        }
        return toReturn;
    }

}
