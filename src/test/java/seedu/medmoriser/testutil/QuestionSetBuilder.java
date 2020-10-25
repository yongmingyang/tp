package seedu.medmoriser.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;
import seedu.medmoriser.model.util.SampleDataUtil;

/**
 * A utility class to help with building QuestionSet objects.
 */
public class QuestionSetBuilder {

    public static final String DEFAULT_QUESTION = "This is a question";
    public static final String DEFAULT_ANSWER = "This is the answer";

    private Question question;
    private Answer answer;
    private Set<Tag> tags;

    /**
     * Creates a {@code QuestionSetBuilder} with the default details.
     */
    public QuestionSetBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFAULT_ANSWER);
        tags = new HashSet<>();
    }

    /**
     * Initializes the QuestionSetBuilder with the data of {@code questionSetToCopy}.
     */
    public QuestionSetBuilder(QAndA qAndAToCopy) {
        question = qAndAToCopy.getQuestion();
        answer = qAndAToCopy.getAnswer();
        tags = new HashSet<>(qAndAToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code QuestionSet} that we are building.
     */
    public QuestionSetBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code QuestionSet} that we are building.
     */
    public QuestionSetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code QuestionSet} that we are building.
     */
    public QuestionSetBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    public QAndA build() {
        return new QAndA(question, answer, tags);
    }

}
