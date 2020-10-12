package seedu.medmoriser.model.qanda;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.model.qanda.exceptions.QuestionSetNotFoundException;

/**
 * A list of questionSets that enforces uniqueness between its elements and does not allow nulls.
 * A questionSet is considered unique by comparing using {@code QuestionSet#isSameQuestionSet(QuestionSet)}.
 * As such, adding and updating of questionSets uses QuestionSet#isSameQuestionSet(QuestionSet)
 * for equality so as to ensure that the questionSet being added or updated is
 * unique in terms of identity in the UniqueQuestionSetList. However, the removal
 * of a questionSet uses QuestionSet#equals(Object) so as to ensure that the questionSet
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see QAndA#isSameQuestionSet(QAndA)
 */
public class UniqueQAndAList implements Iterable<QAndA> {

    private final ObservableList<QAndA> internalList = FXCollections.observableArrayList();
    private final ObservableList<QAndA> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent questionSet as the given argument.
     */
    public boolean contains(QAndA toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameQuestionSet);
    }

    /**
     * Adds a questionSet to the list.
     * The questionSet must not already exist in the list.
     */
    public void add(QAndA toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuestionSetException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the questionSet {@code target} in the list with {@code editedQuestionSet}.
     * {@code target} must exist in the list.
     * The questionSet identity of {@code editedQuestionSet} must not be the same as another existing
     * questionSet in the list.
     */
    public void setQuestionSet(QAndA target, QAndA editedQAndA) {
        requireAllNonNull(target, editedQAndA);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new QuestionSetNotFoundException();
        }

        if (!target.isSameQuestionSet(editedQAndA) && contains(editedQAndA)) {
            throw new DuplicateQuestionSetException();
        }

        internalList.set(index, editedQAndA);
    }

    /**
     * Removes the equivalent questionSet from the list.
     * The questionSet must exist in the list.
     */
    public void remove(QAndA toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new QuestionSetNotFoundException();
        }
    }

    public void setQuestionSets(UniqueQAndAList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code questionSets}.
     * {@code questionSets} must not contain duplicate questionSets.
     */
    public void setQuestionSets(List<QAndA> qAndAs) {
        requireAllNonNull(qAndAs);
        if (!questionSetsAreUnique(qAndAs)) {
            throw new DuplicateQuestionSetException();
        }

        internalList.setAll(qAndAs);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<QAndA> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<QAndA> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQAndAList // instanceof handles nulls
                        && internalList.equals(((UniqueQAndAList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code questionSets} contains only unique questionSet.
     */
    private boolean questionSetsAreUnique(List<QAndA> qAndAs) {
        for (int i = 0; i < qAndAs.size() - 1; i++) {
            for (int j = i + 1; j < qAndAs.size(); j++) {
                if (qAndAs.get(i).isSameQuestionSet(qAndAs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
