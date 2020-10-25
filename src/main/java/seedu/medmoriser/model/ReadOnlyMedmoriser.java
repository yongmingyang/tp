package seedu.medmoriser.model;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Unmodifiable view of a question bank
 */
public interface ReadOnlyMedmoriser {

    /**
     * Returns an unmodifiable view of the qAndAs list.
     * This list will not contain any duplicate qAndAs.
     */
    ObservableList<QAndA> getQAndAList();

}
