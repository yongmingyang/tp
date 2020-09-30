package seedu.medmoriser.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.medmoriser.model.questionset.QuestionSet;

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

    public final QuestionSet questionSet;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code QuestionSetCode} with the given {@code QuestionSet} and index to display.
     */
    public QuestionSetCard(QuestionSet questionSet, int displayedIndex) {
        super(FXML);
        this.questionSet = questionSet;
        id.setText(displayedIndex + ". ");
        question.setText(questionSet.getQuestion().question);
        phone.setText(questionSet.getPhone().value);
        address.setText(questionSet.getAnswer().value);
        email.setText(questionSet.getEmail().value);
        questionSet.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
                && questionSet.equals(card.questionSet);
    }
}
