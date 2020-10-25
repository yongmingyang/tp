package seedu.medmoriser.model.qanda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.testutil.QAndABuilder;

public class QuestionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsKeywordsPredicate firstPredicateCopy =
                new QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different qAndA -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionContainsKeywords_returnsTrue() {
        // One keyword
        QuestionContainsKeywordsPredicate predicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new QAndABuilder().withQuestion("Alice Bob").build()));

        // Multiple keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new QAndABuilder().withQuestion("Alice Bob").build()));

        // Only one matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new QAndABuilder().withQuestion("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new QAndABuilder().withQuestion("Alice Bob").build()));
    }

    @Test
    public void test_questionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsKeywordsPredicate predicate = new QuestionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new QAndABuilder().withQuestion("Alice").build()));

        // Non-matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new QAndABuilder().withQuestion("Alice Bob").build()));

        // Keywords match tag and answer, but does not match question
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("12345", "fakeTag", "Main", "Street"));
        assertFalse(predicate.test(new QAndABuilder().withQuestion("Alice").withPhone("12345")
                .withTags("fakeTag").withAnswer("Main Street").build()));
    }
}
