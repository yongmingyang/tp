package seedu.medmoriser.model.questionset;

import java.util.List;
import java.util.function.Predicate;

import seedu.medmoriser.commons.util.StringUtil;

/**
 * Tests that a {@code QuestionSet}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<QuestionSet> {
    private final List<String> keywords;

    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QuestionSet questionSet) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(questionSet.getQuestion().question, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
