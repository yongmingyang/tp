package seedu.medmoriser.model.qanda;

import seedu.medmoriser.commons.util.StringUtil;
import seedu.medmoriser.model.tag.Tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class TagContainsKeywordsPredicate implements Predicate<QAndA> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(QAndA qAndA) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    Set<Tag> tags = qAndA.getTags();
                    for (Tag tag : tags) {
                        if (StringUtil.containsWordIgnoreCase(tag.tagName, keyword)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
