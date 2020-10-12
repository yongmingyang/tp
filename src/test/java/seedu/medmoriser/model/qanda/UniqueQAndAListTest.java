package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.ALICE;
import static seedu.medmoriser.testutil.TypicalQuestionSet.BOB;

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
        assertFalse(uniqueQAndAList.contains(ALICE));
    }

    @Test
    public void contains_questionSetInList_returnsTrue() {
        uniqueQAndAList.add(ALICE);
        assertTrue(uniqueQAndAList.contains(ALICE));
    }

    @Test
    public void contains_questionSetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQAndAList.add(ALICE);
        QAndA editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueQAndAList.contains(editedAlice));
    }

    @Test
    public void add_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.add(null));
    }

    @Test
    public void add_duplicateQuestionSet_throwsDuplicateQuestionSetException() {
        uniqueQAndAList.add(ALICE);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQAndAList.add(ALICE));
    }

    @Test
    public void setQuestionSet_nullTargetQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.setQuestionSet(null, ALICE));
    }

    @Test
    public void setQuestionSet_nullEditedQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQuestionSet(ALICE, null));
    }

    @Test
    public void setQuestionSet_targetQuestionSetNotInList_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQAndAList.setQuestionSet(ALICE, ALICE));
    }

    @Test
    public void setQuestionSet_editedQuestionSetIsSameQuestionSet_success() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.setQuestionSet(ALICE, ALICE);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(ALICE);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasSameIdentity_success() {
        uniqueQAndAList.add(ALICE);
        QAndA editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueQAndAList.setQuestionSet(ALICE, editedAlice);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(editedAlice);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasDifferentIdentity_success() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.setQuestionSet(ALICE, BOB);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasNonUniqueIdentity_throwsDuplicateQuestionSetException() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.add(BOB);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQAndAList.setQuestionSet(ALICE, BOB));
    }

    @Test
    public void remove_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.remove(null));
    }

    @Test
    public void remove_questionSetDoesNotExist_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQAndAList.remove(ALICE));
    }

    @Test
    public void remove_existingQuestionSet_removesQuestionSet() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.remove(ALICE);
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
        uniqueQAndAList.add(ALICE);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
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
        uniqueQAndAList.add(ALICE);
        List<QAndA> qAndAList = Collections.singletonList(BOB);
        uniqueQAndAList.setQuestionSets(qAndAList);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQuestionSets_listWithDuplicateQuestions_throwsDuplicateQuestionSetException() {
        List<QAndA> listWithDuplicateQAndAs = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateQuestionSetException.class, () ->
            uniqueQAndAList.setQuestionSets(listWithDuplicateQAndAs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueQAndAList.asUnmodifiableObservableList().remove(0));
    }
}
