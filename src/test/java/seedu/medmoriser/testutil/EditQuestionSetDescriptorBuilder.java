package seedu.medmoriser.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.medmoriser.logic.commands.EditCommand.EditQuestionSetDescriptor;
import seedu.medmoriser.model.questionset.Answer;
import seedu.medmoriser.model.questionset.Email;
import seedu.medmoriser.model.questionset.Phone;
import seedu.medmoriser.model.questionset.Question;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.model.tag.Tag;

/**
 * A utility class to help with building EditQuestionSetDescriptor objects.
 */
public class EditQuestionSetDescriptorBuilder {

    private EditQuestionSetDescriptor descriptor;

    public EditQuestionSetDescriptorBuilder() {
        descriptor = new EditQuestionSetDescriptor();
    }

    public EditQuestionSetDescriptorBuilder(EditQuestionSetDescriptor descriptor) {
        this.descriptor = new EditQuestionSetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditQuestionSetDescriptor} with fields containing {@code questionSet}'s details
     */
    public EditQuestionSetDescriptorBuilder(QuestionSet questionSet) {
        descriptor = new EditQuestionSetDescriptor();
        descriptor.setQuestion(questionSet.getQuestion());
        descriptor.setPhone(questionSet.getPhone());
        descriptor.setEmail(questionSet.getEmail());
        descriptor.setAnswer(questionSet.getAnswer());
        descriptor.setTags(questionSet.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditQuestionSetDescriptor} that we are building.
     */
    public EditQuestionSetDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditQuestionSetDescriptor} that we are building.
     */
    public EditQuestionSetDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditQuestionSetDescriptor} that we are building.
     */
    public EditQuestionSetDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code answer} of the {@code EditQuestionSetDescriptor} that we are building.
     */
    public EditQuestionSetDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditQuestionSetDescriptor}
     * that we are building.
     */
    public EditQuestionSetDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditQuestionSetDescriptor build() {
        return descriptor;
    }
}
