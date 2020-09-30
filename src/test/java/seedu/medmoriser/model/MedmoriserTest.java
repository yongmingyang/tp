package seedu.medmoriser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.ALICE;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.model.questionset.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class MedmoriserTest {

    private final Medmoriser medmoriser = new Medmoriser();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), medmoriser.getQuestionSetList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Medmoriser newData = getTypicalAddressBook();
        medmoriser.resetData(newData);
        assertEquals(newData, medmoriser);
    }

    @Test
    public void resetData_withDuplicateQuestionSets_throwsDuplicateQuestionSetException() {
        // Two questionSets with the same identity fields
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<QuestionSet> newQuestionSets = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newQuestionSets);

        assertThrows(DuplicateQuestionSetException.class, () -> medmoriser.resetData(newData));
    }

    @Test
    public void hasQuestionSet_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.hasQuestionSet(null));
    }

    @Test
    public void hasQuestionSet_questionSetNotInAddressBook_returnsFalse() {
        assertFalse(medmoriser.hasQuestionSet(ALICE));
    }

    @Test
    public void hasQuestionSet_questionSetInAddressBook_returnsTrue() {
        medmoriser.addQuestionSet(ALICE);
        assertTrue(medmoriser.hasQuestionSet(ALICE));
    }

    @Test
    public void hasQuestionSet_questionSetWithSameIdentityFieldsInAddressBook_returnsTrue() {
        medmoriser.addQuestionSet(ALICE);
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(medmoriser.hasQuestionSet(editedAlice));
    }

    @Test
    public void getQuestionSetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> medmoriser.getQuestionSetList().remove(0));
    }


    /**
     * A stub ReadOnlyAddressBook whose questionSets list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<QuestionSet> questionSets = FXCollections.observableArrayList();

        AddressBookStub(Collection<QuestionSet> questionSets) {
            this.questionSets.setAll(questionSets);
        }

        @Override
        public ObservableList<QuestionSet> getQuestionSetList() {
            return questionSets;
        }
    }

}
