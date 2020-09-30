package seedu.medmoriser.model.questionset.exceptions;

/**
 * Signals that the operation will result in duplicate QuestionSets (QuestionSets are considered duplicates if they have the same
 * identity).
 */
public class DuplicateQuestionSetException extends RuntimeException {
    public DuplicateQuestionSetException() {
        super("Operation would result in duplicate question sets");
    }
}
