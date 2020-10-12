package seedu.medmoriser.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.medmoriser.logic.commands.EditCommand.EditQuestionSetDescriptor;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Email;
import seedu.medmoriser.model.qanda.Phone;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
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
    public EditQuestionSetDescriptorBuilder(QAndA qAndA) {
        descriptor = new EditQuestionSetDescriptor();
        descriptor.setQuestion(qAndA.getQuestion());
        descriptor.setPhone(qAndA.getPhone());
        descriptor.setEmail(qAndA.getEmail());
        descriptor.setAnswer(qAndA.getAnswer());
        descriptor.setTags(qAndA.getTags());
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
