package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.ALICE;
import static seedu.medmoriser.testutil.TypicalQAndA.BOB;

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
        assertFalse(uniqueQAndAList.contains(ALICE));
    }

    @Test
    public void contains_qAndAInList_returnsTrue() {
        uniqueQAndAList.add(ALICE);
        assertTrue(uniqueQAndAList.contains(ALICE));
    }

    @Test
    public void contains_qAndAWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQAndAList.add(ALICE);
        QAndA editedAlice = new QAndABuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueQAndAList.contains(editedAlice));
    }

    @Test
    public void add_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.add(null));
    }

    @Test
    public void add_duplicateQAndA_throwsDuplicateQAndAException() {
        uniqueQAndAList.add(ALICE);
        assertThrows(DuplicateQAndAException.class, () -> uniqueQAndAList.add(ALICE));
    }

    @Test
    public void setQAndA_nullTargetQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.setQAndA(null, ALICE));
    }

    @Test
    public void setQAndA_nullEditedQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQAndA(ALICE, null));
    }

    @Test
    public void setQAndA_targetQAndANotInList_throwsQAndANotFoundException() {
        assertThrows(QAndANotFoundException.class, () -> uniqueQAndAList.setQAndA(ALICE, ALICE));
    }

    @Test
    public void setQAndA_editedQAndAIsSameQAndA_success() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.setQAndA(ALICE, ALICE);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(ALICE);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQAndAHasSameIdentity_success() {
        uniqueQAndAList.add(ALICE);
        QAndA editedAlice = new QAndABuilder(ALICE).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueQAndAList.setQAndA(ALICE, editedAlice);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(editedAlice);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQAndAHasDifferentIdentity_success() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.setQAndA(ALICE, BOB);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndA_editedQAndAHasNonUniqueIdentity_throwsDuplicateQAndAException() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.add(BOB);
        assertThrows(DuplicateQAndAException.class, () -> uniqueQAndAList.setQAndA(ALICE, BOB));
    }

    @Test
    public void remove_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQAndAList.remove(null));
    }

    @Test
    public void remove_qAndADoesNotExist_throwsQAndANotFoundException() {
        assertThrows(QAndANotFoundException.class, () -> uniqueQAndAList.remove(ALICE));
    }

    @Test
    public void remove_existingQAndA_removesQAndA() {
        uniqueQAndAList.add(ALICE);
        uniqueQAndAList.remove(ALICE);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndAs_nullUniqueQAndAList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueQAndAList.setQAndAs((UniqueQAndAList) null));
    }

    @Test
    public void setQAndAs_uniqueQAndAList_replacesOwnListWithProvidedUniqueQAndAList() {
        uniqueQAndAList.add(ALICE);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
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
        uniqueQAndAList.add(ALICE);
        List<QAndA> qAndAList = Collections.singletonList(BOB);
        uniqueQAndAList.setQAndAs(qAndAList);
        UniqueQAndAList expectedUniqueQAndAList = new UniqueQAndAList();
        expectedUniqueQAndAList.add(BOB);
        assertEquals(expectedUniqueQAndAList, uniqueQAndAList);
    }

    @Test
    public void setQAndAs_listWithDuplicateQuestions_throwsDuplicateQAndAException() {
        List<QAndA> listWithDuplicateQAndAs = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateQAndAException.class, () ->
            uniqueQAndAList.setQAndAs(listWithDuplicateQAndAs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueQAndAList.asUnmodifiableObservableList().remove(0));
    }
}
