package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code QAndA}'s {@code Question} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        for(int i = 0; i < keywords.size(); i++) {
            String curr = keywords.get(i);
            keywords.set(i, curr.toLowerCase());
        }
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    return qAndA.getQuestion().question.toLowerCase().contains(keyword.toLowerCase());
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
