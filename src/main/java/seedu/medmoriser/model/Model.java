package seedu.medmoriser.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<QAndA> PREDICATE_SHOW_ALL_QANDA = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' medmoriser file path.
     */
    Path getMedmoriserFilePath();

    /**
     * Sets the user prefs' medmoriser file path.
     */
    void setMedmoriserFilePath(Path medmoriserFilePath);

    /**
     * Replaces Medmoriser data with the data in {@code Medmoriser}.
     */
    void setMedmoriser(ReadOnlyMedmoriser medmoriser);

    /** Returns the Medmoriser */
    ReadOnlyMedmoriser getMedmoriser();

    /**
     * Returns true if a qAndA with the same identity as {@code qAndA} exists in the medmoriser.
     */
    boolean hasQAndA(QAndA qAndA);

    /**
     * Deletes the given qAndA.
     * The qAndA must exist in the medmoriser.
     */
    void deleteQAndA(QAndA target);

    /**
     * Adds the given qAndA.
     * {@code qAndA} must not already exist in the medmoriser.
     */
    void addQAndA(QAndA qAndA);

    /**
     * Replaces the given qAndA {@code target} with {@code editedQAndA}.
     * {@code target} must exist in the medmoriser.
     * The qAndA identity of {@code editedQAndA} must not be the same as another
     * existing qAndA in the medmoriser.
     */
    void setQAndA(QAndA target, QAndA editedQAndA);

    /** Returns an unmodifiable view of the filtered qAndA list */
    ObservableList<QAndA> getFilteredQAndAList();

    /**
     * Updates the filter of the filtered qAndA list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQAndAList(Predicate<QAndA> predicate);
}
