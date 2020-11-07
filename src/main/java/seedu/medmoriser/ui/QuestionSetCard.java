package seedu.medmoriser.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * An UI component that displays information of a {@code QuestionSet}.
 */
public class QuestionSetCard extends UiPart<Region> {

    private static final String FXML = "QuestionSetListCard.fxml";

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
    public QuestionSetCard(QAndA qAndA, int displayedIndex, boolean isAnswerDisplayed) {
        super(FXML);
        this.qAndA = qAndA;
        id.setText(displayedIndex + ". ");
        question.setText(qAndA.getQuestion().question);
        answer.setText(qAndA.getAnswer().answer);
        qAndA.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagName = new Label(tag.tagName);
                    tagName.setMaxWidth(680);
                    tagName.setMaxHeight(Double.NEGATIVE_INFINITY);
                    tagName.setMinHeight(Double.NEGATIVE_INFINITY);
                    tagName.setWrapText(true);
                    tags.getChildren().add(tagName); }
        );
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
        QuestionSetCard card = (QuestionSetCard) other;
        return id.getText().equals(card.id.getText())
                && qAndA.equals(card.qAndA);
    }
}
