package seedu.medmoriser.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.ReadOnlyMedmoriser;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Medmoriser} with sample data.
 */
public class SampleDataUtil {
    public static QAndA[] getSampleQuestionSets() {
        return new QAndA[] {
            new QAndA(new Question("What is the largest organ in the human body?"), new Answer("Skin"),
                getTagSet("anatomy")),
            new QAndA(new Question("What is the largest bone in the body?"), new Answer("Femur"),
                getTagSet("anatomy", "skeletal system")),
            new QAndA(new Question("Which type of cell is found in the brain?"), new Answer("Neurons"),
                getTagSet("neurology")),
            new QAndA(new Question("People with diabetes are unable to produce or use ______ properly"),
                    new Answer("Insulin"), getTagSet("disease")),
            new QAndA(new Question("_________ is a rare disorder in which the blood does not clot properly"),
                    new Answer("Hemophilia"), getTagSet("cardiology")),
            new QAndA(new Question("Encephalitis refers to inflammation of which organ in the body?"),
                    new Answer("Brain"), getTagSet("neurology"))
        };
    }

    public static ReadOnlyMedmoriser getSampleMedmoriser() {
        Medmoriser sampleMedmoriser = new Medmoriser();
        for (QAndA sampleQAndA : getSampleQuestionSets()) {
            sampleMedmoriser.addQAndA(sampleQAndA);
        }
        return sampleMedmoriser;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
