package seedu.medmoriser.model.questionset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.ALICE;
import static seedu.medmoriser.testutil.TypicalQuestionSet.BOB;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.testutil.QuestionSetBuilder;

public class QuestionSetTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        QuestionSet questionSet = new QuestionSetBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> questionSet.getTags().remove(0));
    }

    @Test
    public void isSameQuestionSet() {
        // same object -> returns true
        assertTrue(ALICE.isSameQuestionSet(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameQuestionSet(null));

        // different phone and email -> returns false
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameQuestionSet(editedAlice));

        // different name -> returns false
        editedAlice = new QuestionSetBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameQuestionSet(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new QuestionSetBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQuestionSet(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new QuestionSetBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQuestionSet(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQuestionSet(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        QuestionSet aliceCopy = new QuestionSetBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different questionSet -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new QuestionSetBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new QuestionSetBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new QuestionSetBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
