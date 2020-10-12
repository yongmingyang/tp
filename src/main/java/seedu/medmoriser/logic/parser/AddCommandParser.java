package seedu.medmoriser.logic.parser;

import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.medmoriser.logic.commands.AddCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Email;
import seedu.medmoriser.model.qanda.Phone;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUESTION,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ANSWER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        QAndA qAndA = new QAndA(question, phone, email, answer, tagList);

        return new AddCommand(qAndA);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
