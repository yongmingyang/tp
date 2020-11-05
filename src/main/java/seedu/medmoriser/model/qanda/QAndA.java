package seedu.medmoriser.model.qanda;

import static seedu.medmoriser.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.medmoriser.model.tag.Tag;

/**
 * Represents a QuestionSet in the Question bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class QAndA {

    // Identity fields
    private final Question question;
    private boolean isQuiz = false;
    private boolean isQuizAnswer = false;
    private boolean beenAnswered = false;

    // Data fields
    private final Answer answer;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * If all fields are present:
     * Every field must be present and not null.
     */
    public QAndA(Question question, Answer answer, Set<Tag> tags) {
        requireAllNonNull(question, answer, tags);
        this.question = question;
        this.answer = answer;
        this.tags.addAll(tags);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Boolean getIsQuiz() {
        return this.isQuiz;
    }

    public Boolean getIsQuizAnwer() {
        return this.isQuizAnswer;
    }

    public void setQuizAnswer() {
        this.isQuizAnswer = true;
    }

    public void setAsQuiz() {
        this.isQuiz = true;
    }

    public void setNotQuiz() {
        this.isQuiz = false;
        this.isQuizAnswer = false;
    }

    public void setBeenAnswered() {
        beenAnswered = true;
    }

    public void setNotBeenAnswered() {
        beenAnswered = false;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both QuestionSets of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two QuestionSets.
     */
    public boolean isSameQAndA(QAndA otherQAndA) {
        if (otherQAndA == this) {
            return true;
        }

        return otherQAndA != null
                && otherQAndA.getQuestion().equals(getQuestion())
                && (otherQAndA.getAnswer().equals(getAnswer()));
    }

    /**
     * Returns true if both QuestionSets have the same identity and data fields.
     * This defines a stronger notion of equality between two QuestionSet.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QAndA)) {
            return false;
        }

        QAndA otherQAndA = (QAndA) other;
        return otherQAndA.getQuestion().equals(getQuestion())
                && otherQAndA.getAnswer().equals(getAnswer())
                && otherQAndA.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer());
        if (getTags().size() > 0) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

}
