package seedu.medmoriser.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * Jackson-friendly version of {@link QAndA}.
 */
class JsonAdaptedQAndA {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "QAndA's %s field is missing!";

    private final String question;
    private final String answer;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedQAndA} with the given qAndA details.
     */
    @JsonCreator
    public JsonAdaptedQAndA(@JsonProperty("name") String question, @JsonProperty("answer") String answer,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.question = question;
        this.answer = answer;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code QAndA} into this class for Jackson use.
     */
    public JsonAdaptedQAndA(QAndA source) {
        question = source.getQuestion().question;
        answer = source.getAnswer().answer;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted qAndA object into the model's {@code QAndA} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted qAndA.
     */
    public QAndA toModelType() throws IllegalValueException {
        final List<Tag> qAndATags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            qAndATags.add(tag.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        final Set<Tag> modelTags = new HashSet<>(qAndATags);
        return new QAndA(modelQuestion, modelAnswer, modelTags);
    }

}
