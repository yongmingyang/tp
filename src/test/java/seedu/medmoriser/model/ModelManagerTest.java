package seedu.medmoriser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QUESTIONSETS;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.ALICE;
import static seedu.medmoriser.testutil.TypicalQuestionSet.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.model.questionset.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Medmoriser(), new Medmoriser(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasQuestionSet_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasQuestionSet(null));
    }

    @Test
    public void hasQuestionSet_questionSetNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasQuestionSet(ALICE));
    }

    @Test
    public void hasQuestionSet_questionSetInAddressBook_returnsTrue() {
        modelManager.addQuestionSet(ALICE);
        assertTrue(modelManager.hasQuestionSet(ALICE));
    }

    @Test
    public void getFilteredQuestionSetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredQuestionSetList().remove(0));
    }

    @Test
    public void equals() {
        Medmoriser medmoriser = new AddressBookBuilder().withQuestionSet(ALICE).withQuestionSet(BENSON).build();
        Medmoriser differentMedmoriser = new Medmoriser();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(medmoriser, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(medmoriser, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMedmoriser, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getQuestion().question.split("\\s+");
        modelManager.updateFilteredQuestionSetList(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(medmoriser, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredQuestionSetList(PREDICATE_SHOW_ALL_QUESTIONSETS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(medmoriser, differentUserPrefs)));
    }
}
