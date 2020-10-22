package seedu.medmoriser.model.qanda;

import org.junit.jupiter.api.Test;
import seedu.medmoriser.testutil.QuestionSetBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AnswerContainsKeywordsPredicate firstPredicate =
                new AnswerContainsKeywordsPredicate(firstPredicateKeywordList);
        AnswerContainsKeywordsPredicate secondPredicate =
                new AnswerContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AnswerContainsKeywordsPredicate firstPredicateCopy =
                new AnswerContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different questionSet -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_answerContainsKeywords_returnsTrue() {
        // One keyword
        AnswerContainsKeywordsPredicate predicate =
                new AnswerContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new QuestionSetBuilder().withAnswer("Alice Bob").build()));

        // Multiple keywords
        predicate = new AnswerContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new QuestionSetBuilder().withAnswer("Alice Bob").build()));

        // Only one matching keyword
        predicate = new AnswerContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new QuestionSetBuilder().withAnswer("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new AnswerContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new QuestionSetBuilder().withAnswer("Alice Bob").build()));
    }

    @Test
    public void test_answerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AnswerContainsKeywordsPredicate predicate = new AnswerContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new QuestionSetBuilder().withAnswer("Alice").build()));

        // Non-matching keyword
        predicate = new AnswerContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new QuestionSetBuilder().withAnswer("Alice Bob").build()));

        // Keywords match question, tag, but does not match answer
        predicate = new AnswerContainsKeywordsPredicate(Arrays.asList("12345", "fakeTag", "Ma1n", "Stre3t"));
        assertFalse(predicate.test(new QuestionSetBuilder().withQuestion("Alice").withPhone("12345")
                .withTags("fakeTag").withAnswer("Main Street").build()));
    }
}