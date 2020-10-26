package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.medmoriser.logic.commands.FindCommand;
import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.AnswerContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new QuizCommand object
 */
public class QuizCommandParser implements Parser<QuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the QuizCommand
     * and returns a QuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuizCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }

        //String[] nameKeywords = trimmedArgs.split("/");
//        String[] nameKeywords = trimmedArgs.split("/|, ");;
//        nameKeywords = trimArg(nameKeywords);
//        String[] keywordsArray;
//        String findType = nameKeywords[0];
//
//        switch (findType) {
//        case "t":
//            keywordsArray = nameKeywords[1].split("\\s+");
//            return new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
//        case "q":
//            keywordsArray = nameKeywords[1].split("\\s+");
//            return new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
//        default:
//            return new QuizCommand(new QAndAContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
//        }
        String[] keywordsArray = trimmedArgs.split("/|, ");;

        if (keywordsArray[0].contains(" ")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        //trim whitespaces
        keywordsArray = trimArg(keywordsArray);

        String findType = keywordsArray[0];

        switch (findType) {
        case "t":
            return new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
        case "q":
            return new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
        default:
            return new QuizCommand(new QAndAContainsKeywordsPredicate(Arrays.asList(keywordsArray)));
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
