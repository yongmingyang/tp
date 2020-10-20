package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.function.Predicate;

import seedu.medmoriser.commons.util.StringUtil;

public class AnswerContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public AnswerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(qAndA.getAnswer().answer, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AnswerContainsKeywordsPredicate) other).keywords)); // state check
    }

}
