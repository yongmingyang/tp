package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.function.Predicate;

import seedu.medmoriser.commons.util.StringUtil;

/**
 * Tests that a {@code QandA}'s {@code Question or Answer} matches any of the keywords given.
 */
public class QAndAContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public QAndAContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    return qAndA.getQuestion().question.toLowerCase().contains(keyword.toLowerCase())
                            || qAndA.getAnswer().answer.toLowerCase().contains(keyword.toLowerCase());
//                            StringUtil.containsWordIgnoreCase(qAndA.getAnswer().answer, keyword)
//                                    || StringUtil.containsWordIgnoreCase(qAndA.getQuestion().question, keyword)
                        }
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QAndAContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QAndAContainsKeywordsPredicate) other).keywords)); // state check
    }

}
