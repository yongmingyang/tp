package seedu.medmoriser.logic.parser;

import seedu.medmoriser.logic.commands.QuizCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.QAndAContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.TagContainsKeywordsPredicate;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class QuizCommandParser {

    public QuizCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        String findType = nameKeywords[0];
        switch (findType) {
            case "t/":
                return new QuizCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "q/":
                return new QuizCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            default:
                return new QuizCommand(new QAndAContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
    }
}
