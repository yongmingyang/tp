package seedu.medmoriser.model.qanda.exceptions;

/**
 * Signals that the operation will result in duplicate QAndAs (QAndAs are considered
 * duplicates if they have the same identity).
 */
public class DuplicateQAndAException extends RuntimeException {
    public DuplicateQAndAException() {
        super("Operation would result in duplicate QAndAs");
    }
}
