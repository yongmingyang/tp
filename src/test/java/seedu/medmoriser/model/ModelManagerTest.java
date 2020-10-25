package seedu.medmoriser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QANDA;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.model.qanda.QuestionContainsKeywordsPredicate;
import seedu.medmoriser.testutil.MedmoriserBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Medmoriser(), new Medmoriser(modelManager.getMedmoriser()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMedmoriserFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMedmoriserFilePath(Paths.get("new/address/book/file/path"));
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
    public void setMedmoriserFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMedmoriserFilePath(null));
    }

    @Test
    public void setMedmoriserFilePath_validPath_setsMedmoriserFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setMedmoriserFilePath(path);
        assertEquals(path, modelManager.getMedmoriserFilePath());
    }

    @Test
    public void hasQAndA_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasQAndA(null));
    }

    @Test
    public void hasQAndA_questionSetNotInMedmoriser_returnsFalse() {
        assertFalse(modelManager.hasQAndA(QUESTION1));
    }

    @Test
    public void hasQuestionSet_questionSetInMedmoriser_returnsTrue() {
        modelManager.addQAndA(QUESTION1);
        assertTrue(modelManager.hasQAndA(QUESTION1));
    }

    @Test
    public void getFilteredQAndAList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredQAndAList().remove(0));
    }

    @Test
    public void equals() {
        Medmoriser medmoriser = new MedmoriserBuilder().withQAndA(QUESTION1).withQAndA(QUESTION2).build();
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
        String[] keywords = QUESTION1.getQuestion().question.split("\\s+");
        modelManager.updateFilteredQAndAList(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords[1])));
        assertFalse(modelManager.equals(new ModelManager(medmoriser, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredQAndAList(PREDICATE_SHOW_ALL_QANDA);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMedmoriserFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(medmoriser, differentUserPrefs)));
    }
}
