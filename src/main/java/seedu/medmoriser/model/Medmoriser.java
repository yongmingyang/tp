package seedu.medmoriser.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.UniqueQAndAList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameQAndA comparison)
 */
public class Medmoriser implements ReadOnlyMedmoriser {

    private final UniqueQAndAList qAndAs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        qAndAs = new UniqueQAndAList();
    }

    public Medmoriser() {}

    /**
     * Creates an AddressBook using the QAndAs in the {@code toBeCopied}
     */
    public Medmoriser(ReadOnlyMedmoriser toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the qAndA list with {@code qAndAs}.
     * {@code qAndAs} must not contain duplicate qAndAs.
     */
    public void setqAndAs(List<QAndA> qAndAs) {
        this.qAndAs.setQAndAs(qAndAs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyMedmoriser newData) {
        requireNonNull(newData);

        setqAndAs(newData.getQAndAList());
    }

    //// qAndA-level operations

    /**
     * Returns true if a qAndA with the same identity as {@code qAndA} exists in the address book.
     */
    public boolean hasQAndA(QAndA qAndA) {
        requireNonNull(qAndA);
        return qAndAs.contains(qAndA);
    }

    /**
     * Adds a qAndA to the address book.
     * The qAndA must not already exist in the address book.
     */
    public void addQAndA(QAndA p) {
        qAndAs.add(p);
    }

    /**
     * Replaces the given qAndA {@code target} in the list with {@code editedQAndA}.
     * {@code target} must exist in the address book.
     * The qAndA identity of {@code editedQAndA} must
     * not be the same as another existing qAndA in the address book.
     */
    public void setQAndA(QAndA target, QAndA editedQAndA) {
        requireNonNull(editedQAndA);

        qAndAs.setQAndA(target, editedQAndA);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeQAndA(QAndA key) {
        qAndAs.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return qAndAs.asUnmodifiableObservableList().size() + " qAndAs";
        // TODO: refine later
    }

    @Override
    public ObservableList<QAndA> getQAndAList() {
        return qAndAs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Medmoriser // instanceof handles nulls
                && qAndAs.equals(((Medmoriser) other).qAndAs));
    }

    @Override
    public int hashCode() {
        return qAndAs.hashCode();
    }
}
