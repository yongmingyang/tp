package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.ALICE;
import static seedu.medmoriser.testutil.TypicalQAndA.BOB;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.testutil.QAndABuilder;

public class QAndATest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        QAndA qAndA = new QAndABuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> qAndA.getTags().remove(0));
    }

    @Test
    public void isSameQAndA() {
        // same object -> returns true
        assertTrue(ALICE.isSameQAndA(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameQAndA(null));

        // different phone and email -> returns false
        QAndA editedAlice = new QAndABuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameQAndA(editedAlice));

        // different name -> returns false
        editedAlice = new QAndABuilder(ALICE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(ALICE.isSameQAndA(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new QAndABuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQAndA(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new QAndABuilder(ALICE).withPhone(VALID_PHONE_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQAndA(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new QAndABuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQAndA(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        QAndA aliceCopy = new QAndABuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different qAndA -> returns false
        assertFalse(ALICE.equals(BOB));

        // different question -> returns false
        QAndA editedAlice = new QAndABuilder(ALICE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new QAndABuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new QAndABuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different answer -> returns false
        editedAlice = new QAndABuilder(ALICE).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new QAndABuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
