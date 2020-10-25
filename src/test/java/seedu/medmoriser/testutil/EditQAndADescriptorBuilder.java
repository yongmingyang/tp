package seedu.medmoriser.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.medmoriser.logic.commands.EditCommand;
import seedu.medmoriser.logic.commands.EditCommand.EditQAndADescriptor;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * A utility class to help with building EditQAndADescriptor objects.
 */
public class EditQAndADescriptorBuilder {

    private EditQAndADescriptor descriptor;

    public EditQAndADescriptorBuilder() {
        descriptor = new EditQAndADescriptor();
    }

    public EditQAndADescriptorBuilder(EditCommand.EditQAndADescriptor descriptor) {
        this.descriptor = new EditQAndADescriptor(descriptor);
    }

    /**
     * Returns an {@code EditQAndADescriptor} with fields containing {@code qAndA}'s details
     */
    public EditQAndADescriptorBuilder(QAndA qAndA) {
        descriptor = new EditCommand.EditQAndADescriptor();
        descriptor.setQuestion(qAndA.getQuestion());
        descriptor.setAnswer(qAndA.getAnswer());
        descriptor.setTags(qAndA.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditQAndADescriptor} that we are building.
     */
    public EditQAndADescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code answer} of the {@code EditQAndADescriptor} that we are building.
     */
    public EditQAndADescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditQAndADescriptor}
     * that we are building.
     */
    public EditQAndADescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditQAndADescriptor build() {
        return descriptor;
    }
}
