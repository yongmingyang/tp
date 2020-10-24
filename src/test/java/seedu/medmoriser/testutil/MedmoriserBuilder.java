package seedu.medmoriser.testutil;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * A utility class to help with building Medmoriser objects.
 * Example usage: <br>
 *     {@code Medmoriser md = new MedmoriserBuilder().withQAndA("John", "Doe").build();}
 */
public class MedmoriserBuilder {

    private Medmoriser medmoriser;

    public MedmoriserBuilder() {
        medmoriser = new Medmoriser();
    }

    public MedmoriserBuilder(Medmoriser medmoriser) {
        this.medmoriser = medmoriser;
    }

    /**
     * Adds a new {@code QAndA} to the {@code Medmoriser} that we are building.
     */
    public MedmoriserBuilder withQAndA(QAndA qAndA) {
        medmoriser.addQAndA(qAndA);
        return this;
    }

    public Medmoriser build() {
        return medmoriser;
    }
}
