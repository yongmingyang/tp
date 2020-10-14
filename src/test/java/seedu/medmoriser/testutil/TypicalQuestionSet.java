package seedu.medmoriser.testutil;

import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * A utility class containing a list of {@code QuestionSet} objects to be used in tests.
 */
public class TypicalQuestionSet {

    public static final QAndA ALICE = new QuestionSetBuilder().withQuestion("Alice Pauline")
            .withAnswer("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final QAndA BENSON = new QuestionSetBuilder().withQuestion("Benson Meier")
            .withAnswer("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final QAndA CARL = new QuestionSetBuilder().withQuestion("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAnswer("wall street").build();
    public static final QAndA DANIEL = new QuestionSetBuilder().withQuestion("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAnswer("10th street").withTags("friends").build();
    public static final QAndA ELLE = new QuestionSetBuilder().withQuestion("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAnswer("michegan ave").build();
    public static final QAndA FIONA = new QuestionSetBuilder().withQuestion("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAnswer("little tokyo").build();
    public static final QAndA GEORGE = new QuestionSetBuilder().withQuestion("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAnswer("4th street").build();

    // Manually added
    public static final QAndA HOON = new QuestionSetBuilder().withQuestion("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAnswer("little india").build();
    public static final QAndA IDA = new QuestionSetBuilder().withQuestion("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAnswer("chicago ave").build();

    // Manually added - QuestionSet's details found in {@code CommandTestUtil}
    public static final QAndA AMY = new QuestionSetBuilder().withQuestion(VALID_QUESTION_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAnswer(VALID_ANSWER_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final QAndA BOB = new QuestionSetBuilder().withQuestion(VALID_QUESTION_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAnswer(VALID_ANSWER_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalQuestionSet() {} // prevents instantiation

    /**
     * Returns a {@code Medmoriser} with all the typical questionSets.
     */
    public static Medmoriser getTypicalMedmoriser() {
        Medmoriser ab = new Medmoriser();
        for (QAndA qAndA : getTypicalQuestionSets()) {
            ab.addQuestionSet(qAndA);
        }
        return ab;
    }

    public static List<QAndA> getTypicalQuestionSets() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
