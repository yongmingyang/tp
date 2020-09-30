package seedu.medmoriser.testutil;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withQuestionSet("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Medmoriser medmoriser;

    public AddressBookBuilder() {
        medmoriser = new Medmoriser();
    }

    public AddressBookBuilder(Medmoriser medmoriser) {
        this.medmoriser = medmoriser;
    }

    /**
     * Adds a new {@code QuestionSet} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withQuestionSet(QuestionSet questionSet) {
        medmoriser.addQuestionSet(questionSet);
        return this;
    }

    public Medmoriser build() {
        return medmoriser;
    }
}
