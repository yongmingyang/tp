package seedu.medmoriser.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.ReadOnlyAddressBook;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_QUESTIONSET = "QuestionSets list contains duplicate questionSet(s).";

    private final List<JsonAdaptedQuestionSet> questionSets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given questionSets.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("questionSets") List<JsonAdaptedQuestionSet> questionSets) {
        this.questionSets.addAll(questionSets);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        questionSets.addAll(source.getQuestionSetList().stream().map(JsonAdaptedQuestionSet::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Medmoriser toModelType() throws IllegalValueException {
        Medmoriser medmoriser = new Medmoriser();
        for (JsonAdaptedQuestionSet jsonAdaptedQuestionSet : questionSets) {
            QuestionSet questionSet = jsonAdaptedQuestionSet.toModelType();
            if (medmoriser.hasQuestionSet(questionSet)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUESTIONSET);
            }
            medmoriser.addQuestionSet(questionSet);
        }
        return medmoriser;
    }

}
