package seedu.medmoriser.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<QuestionSet> PREDICATE_SHOW_ALL_QUESTIONSETS = unused -> true;

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
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a question set with the same identity as {@code question set} exists in the address book.
     */
    boolean hasQuestionSet(QuestionSet questionSet);

    /**
     * Deletes the given question set.
     * The question set must exist in the address book.
     */
    void deleteQuestionSet(QuestionSet target);

    /**
     * Adds the given question set.
     * {@code question set} must not already exist in the address book.
     */
    void addQuestionSet(QuestionSet questionSet);

    /**
     * Replaces the given question set {@code target} with {@code editedquestion set}.
     * {@code target} must exist in the address book.
     * The question set identity of {@code editedquestion set} must not be the same as another existing question set in the address book.
     */
    void setQuestionSet(QuestionSet target, QuestionSet editedQuestionSet);

    /** Returns an unmodifiable view of the filtered question set list */
    ObservableList<QuestionSet> getFilteredQuestionSetList();

    /**
     * Updates the filter of the filtered question set list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQuestionSetList(Predicate<QuestionSet> predicate);
}
