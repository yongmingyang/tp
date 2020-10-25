package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTIONB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.qanda.exceptions.DuplicateQAndAException;
import seedu.medmoriser.model.qanda.exceptions.QAndANotFoundException;
import seedu.medmoriser.testutil.QAndABuilder;

public class UniqueQAndAListTest {

    private final UniqueQAndAList uniqueQAndAList = new UniqueQAndAList();

    @Test
    public void contains_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.contains(null));
    }

    @Test
    public void contains_qAndANotInList_returnsFalse() {
        assertFalse(uniqueQAndAList.contains(QUESTION1));
    }

    @Test
    public void contains_qAndAInList_returnsTrue() {
        uniqueQAndAList.add(QUESTION1);
        assertTrue(uniqueQAndAList.contains(QUESTION1));
    }

    @Test
    public void contains_qAndAWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQAndAList.add(QUESTION1);
        QAndA editedQuestion1 = new QAndABuilder(QUESTION1).withTags(VALID_TAG_TAG2)
                .build();
        assertTrue(uniqueQAndAList.contains(editedQuestion1));
    }

    @Test
    public void add_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.add(null));
    }

    @Test
    public void add_duplicateQAndA_throwsDuplicateQAndAException() {
        uniqueQAndAList.add(QUESTION1);
        assertThrows(DuplicateQAndAException.class, () -> uniqueQAndAList.add(QUESTION1));
    }

    @Test
    public void setQAndA_nullTargetQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.setQAndA(null, QUESTION1));
    }

    @Test
    public void setQAndA_nullEditedQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQAndA(QUESTION1, null));
    }

    @Test
    public void setQAndA_targetQAndANotInList_throwsQAndANotFoundException() {
        assertThrows(QAndANotFoundException.class, () -> uniqueQAndAList.setQAndA(QUESTION1, QUESTION1));
    }

    @Test
    public void setQAndA_editedQAndAIsSameQAndA_success() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.setQAndA(QUESTION1, QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTION1);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQAndAHasSameIdentity_success() {
        uniqueQAndAList.add(QUESTION1);
        QAndA editedQuestion1 = new QAndABuilder(QUESTION1).withAnswer(VALID_ANSWER_B)
                .withTags(VALID_TAG_TAG2)
                .build();
        uniqueQAndAList.setQAndA(QUESTION1, editedQuestion1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(editedQuestion1);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQuestionSetHasDifferentIdentity_success() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.setQAndA(QUESTION1, QUESTIONB);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQuestionSetHasNonUniqueIdentity_throwsDuplicateQuestionSetException() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.add(QUESTIONB);
        assertThrows(DuplicateQAndAException.class, () -> uniqueQAndAList.setQAndA(QUESTION1, QUESTIONB));
    }

    @Test
    public void remove_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.remove(null));
    }

    @Test

    public void remove_qAndADoesNotExist_throwsQuestionSetNotFoundException() {
        assertThrows(QAndANotFoundException.class, () -> uniqueQAndAList.remove(QUESTION1));
    }

    @Test
    public void remove_existingQuestionSet_removesQAndA() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.remove(QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndAs_nullUniqueQAndAList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQAndAs((UniqueQAndAList) null));
    }

    @Test
    public void setQAndAs_uniqueQAndAList_replacesOwnListWithProvidedUniqueQAndAsList() {
        uniqueQAndAList.add(QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        uniqueQAndAList.setQAndAs(expectedUniqueQAndAList);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndAs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQAndAs((List<QAndA>) null));
    }

    @Test
    public void setQAndAs_list_replacesOwnListWithProvidedList() {
        uniqueQAndAList.add(QUESTION1);
        List<QAndA> qAndAList = Collections.singletonList(QUESTIONB);
        uniqueQAndAList.setQAndAs(qAndAList);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndAs_listWithDuplicateQuestions_throwsDuplicateQuestionSetException() {
        List<QAndA> listWithDuplicateQAndAs = Arrays.asList(QUESTION1, QUESTION1);
        assertThrows(DuplicateQAndAException.class, () ->
            uniqueQAndAList.setQAndAs(listWithDuplicateQAndAs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueQAndAList.asUnmodifiableObservableList().remove(0));
    }
}
