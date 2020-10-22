package seedu.medmoriser.model.qanda;

import org.junit.jupiter.api.Test;
import seedu.medmoriser.testutil.QuestionSetBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QAndAContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QAndAContainsKeywordsPredicate firstPredicate =
                new QAndAContainsKeywordsPredicate(firstPredicateKeywordList);
        QAndAContainsKeywordsPredicate secondPredicate =
                new QAndAContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QAndAContainsKeywordsPredicate firstPredicateCopy =
                new QAndAContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different questionSet -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_QAndAContainsKeywords_returnsTrue() {
        // One keyword
        QAndAContainsKeywordsPredicate predicate =
                new QAndAContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new QuestionSetBuilder().withQuestion("Charlie").withAnswer("Alice Bob").build()));

        // Multiple keywords
        predicate = new QAndAContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new QuestionSetBuilder().withQuestion("Bob").withAnswer("Alice").build()));

        // Only one matching keyword
        predicate = new QAndAContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new QuestionSetBuilder().withQuestion("Tom").withAnswer("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new QAndAContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new QuestionSetBuilder().withQuestion("Bob").withAnswer("Alice").build()));
    }

    @Test
    public void test_QAndADoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QAndAContainsKeywordsPredicate predicate = new QAndAContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new QuestionSetBuilder().withQuestion("Bob").withAnswer("Alice").build()));

        // Non-matching keyword
        predicate = new QAndAContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new QuestionSetBuilder().withQuestion("Tom").withAnswer("Alice Bob").build()));

        // Keywords match tag, but does not match Question or Answer
        predicate = new QAndAContainsKeywordsPredicate(Arrays.asList("12345", "fakeTag", "Ma1n", "Stre3t"));
        assertFalse(predicate.test(new QuestionSetBuilder().withQuestion("Alice")
                .withTags("fakeTag").withAnswer("Main Street").build()));
    }
}
