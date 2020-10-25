package seedu.medmoriser.testutil;

import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_A;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_B;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG1;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_TAG2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * A utility class containing a list of {@code QAndA} objects to be used in tests.
 */
public class TypicalQAndA {

    public static final QAndA QUESTION1 = new QAndABuilder().withQuestion("Question One")
            .withAnswer("Answer 1")
            .withTags("DNA").build();
    public static final QAndA QUESTION2 = new QAndABuilder().withQuestion("Question Two")
            .withAnswer("Answer 2")
            .withTags("ImmuneSystem").build();
    public static final QAndA QUESTION3 = new QAndABuilder().withQuestion("Question Three")
            .withAnswer("Answer 3").build();
    public static final QAndA QUESTION4 = new QAndABuilder().withQuestion("Question Four")
            .withAnswer("Answer 4").build();
    public static final QAndA QUESTION5 = new QAndABuilder().withQuestion("Question Five")
            .withAnswer("Answer 5").build();
    public static final QAndA QUESTION6 = new QAndABuilder().withQuestion("Question Six")
            .withAnswer("Answer 6")
            .build();
    public static final QAndA QUESTION7 = new QAndABuilder().withQuestion("Question Seven")
            .withAnswer("Answer 7")
            .build();

    // Manually added
    public static final QAndA QUESTION8 = new QAndABuilder().withQuestion("Question Eight")
            .withAnswer("Answer 8").build();
    public static final QAndA QUESTION9 = new QAndABuilder().withQuestion("Question Nine")
            .withAnswer("Answer 9").build();

    // Manually added - QuestionSet's details found in {@code CommandTestUtil}
    public static final QAndA QUESTIONA = new QAndABuilder().withQuestion(VALID_QUESTION_A)
            .withAnswer(VALID_ANSWER_A).withTags(VALID_TAG_TAG1).build();
    public static final QAndA QUESTIONB = new QAndABuilder().withQuestion(VALID_QUESTION_B)
            .withAnswer(VALID_ANSWER_B).withTags(VALID_TAG_TAG2, VALID_TAG_TAG1).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalQAndA() {} // prevents instantiation

    /**
     * Returns a {@code Medmoriser} with all the typical qAndAs.
     */
    public static Medmoriser getTypicalMedmoriser() {
        Medmoriser ab = new Medmoriser();
        for (QAndA qAndA : getTypicalQAndAs()) {
            ab.addQAndA(qAndA);
        }
        return ab;
    }

    public static List<QAndA> getTypicalQAndAs() {
        return new ArrayList<>(Arrays.asList(QUESTION1, QUESTION2, QUESTION3, QUESTION4, QUESTION5, QUESTION6,
                QUESTION7));

    }
}
