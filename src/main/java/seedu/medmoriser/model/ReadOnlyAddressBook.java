package seedu.medmoriser.model;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the questionSets list.
     * This list will not contain any duplicate questionSets.
     */
    ObservableList<QuestionSet> getQuestionSetList();

}
