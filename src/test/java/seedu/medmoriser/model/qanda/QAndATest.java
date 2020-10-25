package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTIONB;

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
        assertTrue(QUESTION1.isSameQAndA(QUESTION1));

        // null -> returns false
        assertFalse(QUESTION1.isSameQAndA(null));

        QAndA editedQuestion1;

        // different question -> returns false
        editedQuestion1 = new QAndABuilder(QUESTION1).withQuestion(VALID_QUESTION_B).build();
        assertFalse(QUESTION1.isSameQAndA(editedQuestion1));

        // same question, different answer -> returns false
        editedQuestion1 = new QAndABuilder(QUESTION1).withAnswer(VALID_ANSWER_B)
                .withTags(VALID_TAG_TAG2).build();
        assertFalse(QUESTION1.isSameQAndA(editedQuestion1));

        // same question, same answer, different tag -> returns true
        editedQuestion1 = new QAndABuilder(QUESTION1).withTags(VALID_TAG_TAG2).build();
        assertTrue(QUESTION1.isSameQAndA(editedQuestion1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        QAndA question1Copy = new QAndABuilder(QUESTION1).build();
        assertTrue(QUESTION1.equals(question1Copy));

        // same object -> returns true
        assertTrue(QUESTION1.equals(QUESTION1));

        // null -> returns false
        assertFalse(QUESTION1.equals(null));

        // different type -> returns false
        assertFalse(QUESTION1.equals(5));

        // different questionSet -> returns false
        assertFalse(QUESTION1.equals(QUESTIONB));

        // different question -> returns false
        QAndA editedQuestion1 = new QAndABuilder(QUESTION1).withQuestion(VALID_QUESTION_B).build();
        assertFalse(QUESTION1.equals(editedQuestion1));

        // different answer -> returns false
        editedQuestion1 = new QAndABuilder(QUESTION1).withAnswer(VALID_ANSWER_B).build();
        assertFalse(QUESTION1.equals(editedQuestion1));

        // different tags -> returns false
        editedQuestion1 = new QAndABuilder(QUESTION1).withTags(VALID_TAG_TAG2).build();
        assertFalse(QUESTION1.equals(editedQuestion1));
    }
}
