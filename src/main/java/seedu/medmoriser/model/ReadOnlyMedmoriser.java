package seedu.medmoriser.model;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Unmodifiable view of a question bank
 */
public interface ReadOnlyMedmoriser {

    /**
     * Returns an unmodifiable view of the questionSets list.
     * This list will not contain any duplicate questionSets.
     */
    ObservableList<QAndA> getQuestionSetList();

}
