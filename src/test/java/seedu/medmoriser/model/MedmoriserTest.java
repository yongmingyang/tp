package seedu.medmoriser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQuestionSet.getTypicalMedmoriser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class MedmoriserTest {

    private final Medmoriser medmoriser = new Medmoriser();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), medmoriser.getQAndAList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMedmoriser_replacesData() {
        Medmoriser newData = getTypicalMedmoriser();
        medmoriser.resetData(newData);
        assertEquals(newData, medmoriser);
    }

    @Test
    public void resetData_withDuplicateQuestionSets_throwsDuplicateQuestionSetException() {
        // Two questionSets with the same identity fields
        QAndA editedQuestion1 = new QuestionSetBuilder(QUESTION1).withTags(VALID_TAG_TAG2)
                .build();
        List<QAndA> newQAndAs = Arrays.asList(QUESTION1, editedQuestion1);
        MedmoriserStub newData = new MedmoriserStub(newQAndAs);

        assertThrows(DuplicateQuestionSetException.class, () -> medmoriser.resetData(newData));
    }

    @Test
    public void hasQuestionSet_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.hasQuestionSet(null));
    }

    @Test
    public void hasQuestionSet_questionSetNotInMedmoriser_returnsFalse() {
        assertFalse(medmoriser.hasQuestionSet(QUESTION1));
    }

    @Test
    public void hasQuestionSet_questionSetInMedmoriser_returnsTrue() {
        medmoriser.addQuestionSet(QUESTION1);
        assertTrue(medmoriser.hasQuestionSet(QUESTION1));
    }

    @Test
    public void hasQuestionSet_questionSetWithSameIdentityFieldsInMedmoriser_returnsTrue() {
        medmoriser.addQuestionSet(QUESTION1);
        QAndA editedQuestion1 = new QuestionSetBuilder(QUESTION1).withTags(VALID_TAG_TAG2)
                .build();
        assertTrue(medmoriser.hasQuestionSet(editedQuestion1));
    }

    @Test
    public void getQuestionSetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> medmoriser.getQAndAList().remove(0));
    }


    /**
     * A stub ReadOnlyAddressBook whose questionSets list can violate interface constraints.
     */
    private static class MedmoriserStub implements ReadOnlyMedmoriser {
        private final ObservableList<QAndA> qAndAs = FXCollections.observableArrayList();

        MedmoriserStub(Collection<QAndA> qAndAs) {
            this.qAndAs.setAll(qAndAs);
        }

        @Override
        public ObservableList<QAndA> getQAndAList() {
            return qAndAs;
        }
    }

}
