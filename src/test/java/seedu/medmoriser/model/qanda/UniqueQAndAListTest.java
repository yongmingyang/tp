package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTIONB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.qanda.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.model.qanda.exceptions.QuestionSetNotFoundException;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class UniqueQAndAListTest {

    private final UniqueQAndAList uniqueQAndAList = new UniqueQAndAList();

    @Test
    public void contains_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.contains(null));
    }

    @Test
    public void contains_questionSetNotInList_returnsFalse() {
        assertFalse(uniqueQAndAList.contains(QUESTION1));
    }

    @Test
    public void contains_questionSetInList_returnsTrue() {
        uniqueQAndAList.add(QUESTION1);
        assertTrue(uniqueQAndAList.contains(QUESTION1));
    }

    @Test
    public void contains_questionSetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQAndAList.add(QUESTION1);
        QAndA editedQuestion1 = new QuestionSetBuilder(QUESTION1).withTags(VALID_TAG_TAG2)
                .build();
        assertTrue(uniqueQAndAList.contains(editedQuestion1));
    }

    @Test
    public void add_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.add(null));
    }

    @Test
    public void add_duplicateQuestionSet_throwsDuplicateQuestionSetException() {
        uniqueQAndAList.add(QUESTION1);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQAndAList.add(QUESTION1));
    }

    @Test
    public void setQuestionSet_nullTargetQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.setQuestionSet(null, QUESTION1));
    }

    @Test
    public void setQuestionSet_nullEditedQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQuestionSet(QUESTION1, null));
    }

    @Test
    public void setQuestionSet_targetQuestionSetNotInList_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQAndAList.setQuestionSet(QUESTION1, QUESTION1));
    }

    @Test
    public void setQuestionSet_editedQuestionSetIsSameQuestionSet_success() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.setQuestionSet(QUESTION1, QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTION1);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasSameIdentity_success() {
        uniqueQAndAList.add(QUESTION1);
        QAndA editedQuestion1 = new QuestionSetBuilder(QUESTION1).withAnswer(VALID_ANSWER_B)
                .withTags(VALID_TAG_TAG2)
                .build();
        uniqueQAndAList.setQuestionSet(QUESTION1, editedQuestion1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(editedQuestion1);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasDifferentIdentity_success() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.setQuestionSet(QUESTION1, QUESTIONB);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasNonUniqueIdentity_throwsDuplicateQuestionSetException() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.add(QUESTIONB);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQAndAList.setQuestionSet(QUESTION1, QUESTIONB));
    }

    @Test
    public void remove_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.remove(null));
    }

    @Test
    public void remove_questionSetDoesNotExist_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQAndAList.remove(QUESTION1));
    }

    @Test
    public void remove_existingQuestionSet_removesQuestionSet() {
        uniqueQAndAList.add(QUESTION1);
        uniqueQAndAList.remove(QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSets_nullUniqueQuestionSetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQuestionSets((UniqueQAndAList) null));
    }

    @Test
    public void setQuestionSets_uniqueQuestionSetList_replacesOwnListWithProvidedUniqueQuestionSetList() {
        uniqueQAndAList.add(QUESTION1);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        uniqueQAndAList.setQuestionSets(expectedUniqueQAndAList);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQuestionSets((List<QAndA>) null));
    }

    @Test
    public void setQuestionSets_list_replacesOwnListWithProvidedList() {
        uniqueQAndAList.add(QUESTION1);
        List<QAndA> qAndAList = Collections.singletonList(QUESTIONB);
        uniqueQAndAList.setQuestionSets(qAndAList);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(QUESTIONB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSets_listWithDuplicateQuestions_throwsDuplicateQuestionSetException() {
        List<QAndA> listWithDuplicateQAndAs = Arrays.asList(QUESTION1, QUESTION1);
        assertThrows(DuplicateQuestionSetException.class, () ->
            uniqueQAndAList.setQuestionSets(listWithDuplicateQAndAs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueQAndAList.asUnmodifiableObservableList().remove(0));
    }
}
