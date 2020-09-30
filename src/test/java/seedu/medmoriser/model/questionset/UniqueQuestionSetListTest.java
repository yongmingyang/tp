package seedu.medmoriser.model.questionset;

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

import seedu.medmoriser.model.questionset.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.model.questionset.exceptions.QuestionSetNotFoundException;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class UniqueQuestionSetListTest {

    private final UniqueQuestionSetList uniqueQuestionSetList = new UniqueQuestionSetList();

    @Test
    public void contains_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.contains(null));
    }

    @Test
    public void contains_questionSetNotInList_returnsFalse() {
        assertFalse(uniqueQuestionSetList.contains(ALICE));
    }

    @Test
    public void contains_questionSetInList_returnsTrue() {
        uniqueQuestionSetList.add(ALICE);
        assertTrue(uniqueQuestionSetList.contains(ALICE));
    }

    @Test
    public void contains_questionSetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQuestionSetList.add(ALICE);
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueQuestionSetList.contains(editedAlice));
    }

    @Test
    public void add_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.add(null));
    }

    @Test
    public void add_duplicateQuestionSet_throwsDuplicateQuestionSetException() {
        uniqueQuestionSetList.add(ALICE);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQuestionSetList.add(ALICE));
    }

    @Test
    public void setQuestionSet_nullTargetQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.setQuestionSet(null, ALICE));
    }

    @Test
    public void setQuestionSet_nullEditedQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.setQuestionSet(ALICE, null));
    }

    @Test
    public void setQuestionSet_targetQuestionSetNotInList_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQuestionSetList.setQuestionSet(ALICE, ALICE));
    }

    @Test
    public void setQuestionSet_editedQuestionSetIsSameQuestionSet_success() {
        uniqueQuestionSetList.add(ALICE);
        uniqueQuestionSetList.setQuestionSet(ALICE, ALICE);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        expectedUniqueQuestionSetList.add(ALICE);
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasSameIdentity_success() {
        uniqueQuestionSetList.add(ALICE);
        QuestionSet editedAlice = new QuestionSetBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueQuestionSetList.setQuestionSet(ALICE, editedAlice);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        expectedUniqueQuestionSetList.add(editedAlice);
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasDifferentIdentity_success() {
        uniqueQuestionSetList.add(ALICE);
        uniqueQuestionSetList.setQuestionSet(ALICE, BOB);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        expectedUniqueQuestionSetList.add(BOB);
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSet_editedQuestionSetHasNonUniqueIdentity_throwsDuplicateQuestionSetException() {
        uniqueQuestionSetList.add(ALICE);
        uniqueQuestionSetList.add(BOB);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQuestionSetList.setQuestionSet(ALICE, BOB));
    }

    @Test
    public void remove_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.remove(null));
    }

    @Test
    public void remove_questionSetDoesNotExist_throwsQuestionSetNotFoundException() {
        assertThrows(QuestionSetNotFoundException.class, () -> uniqueQuestionSetList.remove(ALICE));
    }

    @Test
    public void remove_existingQuestionSet_removesQuestionSet() {
        uniqueQuestionSetList.add(ALICE);
        uniqueQuestionSetList.remove(ALICE);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSets_nullUniqueQuestionSetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.setQuestionSets((UniqueQuestionSetList) null));
    }

    @Test
    public void setQuestionSets_uniqueQuestionSetList_replacesOwnListWithProvidedUniqueQuestionSetList() {
        uniqueQuestionSetList.add(ALICE);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        expectedUniqueQuestionSetList.add(BOB);
        uniqueQuestionSetList.setQuestionSets(expectedUniqueQuestionSetList);
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionSetList.setQuestionSets((List<QuestionSet>) null));
    }

    @Test
    public void setQuestionSets_list_replacesOwnListWithProvidedList() {
        uniqueQuestionSetList.add(ALICE);
        List<QuestionSet> questionSetList = Collections.singletonList(BOB);
        uniqueQuestionSetList.setQuestionSets(questionSetList);
        UniqueQuestionSetList expectedUniqueQuestionSetList = new UniqueQuestionSetList();
        expectedUniqueQuestionSetList.add(BOB);
        assertEquals(expectedUniqueQuestionSetList, uniqueQuestionSetList);
    }

    @Test
    public void setQuestionSets_listWithDuplicateQuestions_throwsDuplicateQuestionSetException() {
        List<QuestionSet> listWithDuplicateQuestionSets = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateQuestionSetException.class, () -> uniqueQuestionSetList.setQuestionSets(listWithDuplicateQuestionSets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueQuestionSetList.asUnmodifiableObservableList().remove(0));
    }
}
