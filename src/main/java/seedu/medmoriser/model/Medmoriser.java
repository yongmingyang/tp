package seedu.medmoriser.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.model.questionset.UniqueQuestionSetList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameQuestionSet comparison)
 */
public class Medmoriser implements ReadOnlyMedmoriser {

    private final UniqueQuestionSetList questionSets;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        questionSets = new UniqueQuestionSetList();
    }

    public Medmoriser() {}

    /**
     * Creates an AddressBook using the QuestionSets in the {@code toBeCopied}
     */
    public Medmoriser(ReadOnlyMedmoriser toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the questionSet list with {@code questionSets}.
     * {@code questionSets} must not contain duplicate questionSets.
     */
    public void setQuestionSets(List<QuestionSet> questionSets) {
        this.questionSets.setQuestionSets(questionSets);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyMedmoriser newData) {
        requireNonNull(newData);

        setQuestionSets(newData.getQuestionSetList());
    }

    //// questionSet-level operations

    /**
     * Returns true if a questionSet with the same identity as {@code questionSet} exists in the address book.
     */
    public boolean hasQuestionSet(QuestionSet questionSet) {
        requireNonNull(questionSet);
        return questionSets.contains(questionSet);
    }

    /**
     * Adds a questionSet to the address book.
     * The questionSet must not already exist in the address book.
     */
    public void addQuestionSet(QuestionSet p) {
        questionSets.add(p);
    }

    /**
     * Replaces the given questionSet {@code target} in the list with {@code editedQuestionSet}.
     * {@code target} must exist in the address book.
     * The questionSet identity of {@code editedQuestionSet} must
     * not be the same as another existing questionSet in the address book.
     */
    public void setQuestionSet(QuestionSet target, QuestionSet editedQuestionSet) {
        requireNonNull(editedQuestionSet);

        questionSets.setQuestionSet(target, editedQuestionSet);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeQuestionSet(QuestionSet key) {
        questionSets.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return questionSets.asUnmodifiableObservableList().size() + " questionSets";
        // TODO: refine later
    }

    @Override
    public ObservableList<QuestionSet> getQuestionSetList() {
        return questionSets.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Medmoriser // instanceof handles nulls
                && questionSets.equals(((Medmoriser) other).questionSets));
    }

    @Override
    public int hashCode() {
        return questionSets.hashCode();
    }
}
