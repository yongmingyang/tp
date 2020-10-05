package seedu.medmoriser.testutil;

import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.medmoriser.logic.commands.AddCommand;
import seedu.medmoriser.logic.commands.EditCommand;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.model.tag.Tag;

/**
 * A utility class for QuestionSet.
 */
public class QuestionSetUtil {

    /**
     * Returns an add command string for adding the {@code questionSet}.
     */
    public static String getAddCommand(QuestionSet questionSet) {
        return AddCommand.COMMAND_WORD + " " + getQuestionSetDetails(questionSet);
    }

    /**
     * Returns the part of command string for the given {@code questionSet}'s details.
     */
    public static String getQuestionSetDetails(QuestionSet questionSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + questionSet.getQuestion().question + " ");
        sb.append(PREFIX_PHONE + questionSet.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + questionSet.getEmail().value + " ");
        sb.append(PREFIX_ANSWER + questionSet.getAnswer().value + " ");
        questionSet.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditQuestionSetDescriptor}'s details.
     */
    public static String getEditQuestionSetDescriptorDetails(EditCommand.EditQuestionSetDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(name -> sb.append(PREFIX_QUESTION).append(name.question).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAnswer().ifPresent(address -> sb.append(PREFIX_ANSWER).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
