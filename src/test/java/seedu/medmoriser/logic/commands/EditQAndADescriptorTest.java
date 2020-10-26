package seedu.medmoriser.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.DESC_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.DESC_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.testutil.EditQAndADescriptorBuilder;

public class EditQAndADescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditQAndADescriptor descriptorWithSameValues = new EditCommand
                .EditQAndADescriptor(DESC_A);
        assertTrue(DESC_A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_A.equals(DESC_A));

        // null -> returns false
        assertFalse(DESC_A.equals(null));

        // different types -> returns false
        assertFalse(DESC_A.equals(5));

        // different values -> returns false
        assertFalse(DESC_A.equals(DESC_B));

        // different question -> returns false
        EditCommand.EditQAndADescriptor editedAmy = new EditQAndADescriptorBuilder(DESC_A)
                .withQuestion(VALID_QUESTION_B).build();
        assertFalse(DESC_A.equals(editedAmy));

        // different answer -> returns false
        editedAmy = new EditQAndADescriptorBuilder(DESC_A).withAnswer(VALID_ANSWER_B).build();
        assertFalse(DESC_A.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditQAndADescriptorBuilder(DESC_A).withTags(VALID_TAG_TAG2).build();
        assertFalse(DESC_A.equals(editedAmy));
    }
}
