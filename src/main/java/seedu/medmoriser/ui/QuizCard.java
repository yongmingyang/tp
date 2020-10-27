package seedu.medmoriser.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.medmoriser.model.qanda.QAndA;

public class QuizCard extends UiPart<Region> {
    private static final String FXML = "QuizCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final QAndA qAndA;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label id;
    @FXML
    private Label answer;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code QuestionSetCode} with the given {@code QuestionSet} and index to display.
     */
    public QuizCard(QAndA qAndA, int displayedIndex, boolean isAnswerDisplayed) {
        super(FXML);
        this.qAndA = qAndA;
        id.setText("");
        question.setText(qAndA.getQuestion().question);
        answer.setText(qAndA.getAnswer().answer);
        qAndA.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (!isAnswerDisplayed) {
            hideAnswer();
        }
    }

    public void hideAnswer() {
        this.answer.setVisible(false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionSetCard)) {
            return false;
        }

        // state check
        QuizCard card = (QuizCard) other;
        return id.getText().equals(card.id.getText())
                && qAndA.equals(card.qAndA);
    }
}
