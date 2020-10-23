package seedu.medmoriser.logic.parser;

import seedu.medmoriser.logic.commands.AnswerCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AnswerCommandParser {

    public AnswerCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }
        return new AnswerCommand(args);
    }
}
