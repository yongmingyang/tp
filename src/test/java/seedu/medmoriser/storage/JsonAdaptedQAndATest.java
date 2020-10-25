package seedu.medmoriser.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.medmoriser.storage.JsonAdaptedQAndA.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.QUESTION2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Question;

public class JsonAdaptedQAndATest {
    private static final String INVALID_QUESTION = "R@chel";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_QUESTION = QUESTION2.getQuestion().toString();
    private static final String VALID_ANSWER = QUESTION2.getAnswer().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = QUESTION2.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validQuestionSetDetails_returnsQuestionSet() throws Exception {
        JsonAdaptedQAndA questionSet = new JsonAdaptedQAndA(QUESTION2);
        assertEquals(QUESTION2, questionSet.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedQAndA questionSet =
                new JsonAdaptedQAndA(INVALID_QUESTION, VALID_ANSWER, VALID_TAGS);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, questionSet::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedQAndA questionSet = new JsonAdaptedQAndA(null, VALID_ANSWER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, questionSet::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedQAndA questionSize =
                new JsonAdaptedQAndA(VALID_QUESTION, INVALID_ANSWER, VALID_TAGS);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, questionSize::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedQAndA questionSize = new JsonAdaptedQAndA(VALID_QUESTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, questionSize::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedQAndA questionSet =
                new JsonAdaptedQAndA(VALID_QUESTION, VALID_ANSWER, invalidTags);
        assertThrows(IllegalValueException.class, questionSet::toModelType);
    }

}
