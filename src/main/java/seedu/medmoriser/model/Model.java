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
    Predicate<QAndA> PREDICATE_SHOW_ALL_QUESTIONSETS = unused -> true;

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
     * Returns the user prefs' address book file path.
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
     * Returns true if a questionSet with the same identity as {@code questionSet} exists in the address book.
     */
    boolean hasQuestionSet(QAndA qAndA);

    /**
     * Deletes the given questionSet.
     * The questionSet must exist in the address book.
     */
    void deleteQuestionSet(QAndA target);

    /**
     * Adds the given questionSet.
     * {@code questionSet} must not already exist in the address book.
     */
    void addQuestionSet(QAndA qAndA);

    /**
     * Replaces the given questionSet {@code target} with {@code editedquestionSet}.
     * {@code target} must exist in the address book.
     * The questionSet identity of {@code editedquestionSet} must not be the same as another
     * existing questionSet in the address book.
     */
    void setQuestionSet(QAndA target, QAndA editedQAndA);

    /** Returns an unmodifiable view of the filtered questionSet list */
    ObservableList<QAndA> getFilteredQuestionSetList();

    /**
     * Updates the filter of the filtered questionSet list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQuestionSetList(Predicate<QAndA> predicate);
}
