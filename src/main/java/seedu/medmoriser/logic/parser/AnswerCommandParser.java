package seedu.medmoriser.logic.parser;

import seedu.medmoriser.logic.commands.AnswerCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns an AnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }
        return new AnswerCommand(args);
    }
}
