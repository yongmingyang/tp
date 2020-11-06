package seedu.medmoriser.model.qanda;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.exceptions.DuplicateQAndAException;
import seedu.medmoriser.model.qanda.exceptions.QAndANotFoundException;

/**
 * A list of questionSets that enforces uniqueness between its elements and does not allow nulls.
 * A questionSet is considered unique by comparing using {@code QuestionSet#isSameQAndA(QuestionSet)}.
 * As such, adding and updating of questionSets uses QuestionSet#isSameQAndA(QuestionSet)
 * for equality so as to ensure that the questionSet being added or updated is
 * unique in terms of identity in the UniqueQuestionSetList. However, the removal
 * of a questionSet uses QuestionSet#equals(Object) so as to ensure that the questionSet
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see QAndA#isSameQAndA(QAndA)
 */
public class UniqueQAndAList implements Iterable<QAndA> {

    private final ObservableList<QAndA> internalList = FXCollections.observableArrayList();
    private final ObservableList<QAndA> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent qAndA as the given argument.
     */
    public boolean contains(QAndA toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameQAndA);
    }

    /**
     * Adds a qAndA to the list.
     * The qAndA must not already exist in the list.
     */
    public void add(QAndA toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQAndAException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the qAndA {@code target} in the list with {@code editedQuestionSet}.
     * {@code target} must exist in the list.
     * The questionSet identity of {@code editedQuestionSet} must not be the same as another existing
     * questionSet in the list.
     */
    public void setQAndA(QAndA target, QAndA editedQAndA) {
        requireAllNonNull(target, editedQAndA);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new QAndANotFoundException();
        }

        if (!target.isSameQAndA(editedQAndA) && contains(editedQAndA)) {
            throw new DuplicateQAndAException();
        }

        internalList.set(index, editedQAndA);
    }

    /**
     * Removes the equivalent qAndA from the list.
     * The qAndA must exist in the list.
     */
    public void remove(QAndA toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new QAndANotFoundException();
        }
    }

    public void setQAndAs(UniqueQAndAList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code qAndAs}.
     * {@code qAndAs} must not contain duplicate qAndAs.
     */
    public void setQAndAs(List<QAndA> qAndAs) {
        requireAllNonNull(qAndAs);
        if (!questionSetsAreUnique(qAndAs)) {
            throw new DuplicateQAndAException();
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
     * Returns true if {@code qAndAs} contains only unique qAndAs.
     */
    private boolean questionSetsAreUnique(List<QAndA> qAndAs) {
        for (int i = 0; i < qAndAs.size() - 1; i++) {
            for (int j = i + 1; j < qAndAs.size(); j++) {
                if (qAndAs.get(i).isSameQAndA(qAndAs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
