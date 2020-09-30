package seedu.medmoriser.model;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * Unmodifiable view of a question bank
 */
public interface ReadOnlyMedmoriser {

    /**
     * Returns an unmodifiable view of the questionSets list.
     * This list will not contain any duplicate questionSets.
     */
    ObservableList<QuestionSet> getQuestionSetList();

}
