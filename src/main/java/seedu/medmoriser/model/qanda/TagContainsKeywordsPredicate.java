package seedu.medmoriser.model.qanda;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.medmoriser.model.tag.Tag;

/**
 * Tests that a {@code QAndA}'s {@code Tag} matches any of the keywords given.
 */
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
                        String lowercase = tag.tagName.toLowerCase();
                        if (lowercase.equals(keyword.toLowerCase())) {
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
