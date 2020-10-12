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
import seedu.medmoriser.model.qanda.Email;
import seedu.medmoriser.model.qanda.Phone;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * Jackson-friendly version of {@link QAndA}.
 */
class JsonAdaptedQAndA {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "QuestionSet's %s field is missing!";

    private final String question;
    private final String phone;
    private final String email;
    private final String answer;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedQuestionSet} with the given questionSet details.
     */
    @JsonCreator
    public JsonAdaptedQAndA(@JsonProperty("name") String question, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("address") String address,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.question = question;
        this.phone = phone;
        this.email = email;
        this.answer = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code QuestionSet} into this class for Jackson use.
     */
    public JsonAdaptedQAndA(QAndA source) {
        question = source.getQuestion().question;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        answer = source.getAnswer().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted questionSet object into the model's {@code QuestionSet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted questionSet.
     */
    public QAndA toModelType() throws IllegalValueException {
        final List<Tag> questionSetTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            questionSetTags.add(tag.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        final Set<Tag> modelTags = new HashSet<>(questionSetTags);
        return new QAndA(modelQuestion, modelPhone, modelEmail, modelAnswer, modelTags);
    }

}
