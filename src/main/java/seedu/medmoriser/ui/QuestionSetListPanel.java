package seedu.medmoriser.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.medmoriser.commons.core.LogsCenter;
import seedu.medmoriser.model.questionset.QuestionSet;

/**
 * Panel containing the list of question set.
 */
public class QuestionSetListPanel extends UiPart<Region> {
    private static final String FXML = "QuestionSetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionSetListPanel.class);

    @FXML
    private ListView<QuestionSet> questionSetListView;

    /**
     * Creates a {@code QuestionSetListPanel} with the given {@code ObservableList}.
     */
    public QuestionSetListPanel(ObservableList<QuestionSet> questionSetList) {
        super(FXML);
        questionSetListView.setItems(questionSetList);
        questionSetListView.setCellFactory(listView -> new QuestionSetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QuestionSet} using a {@code QuestionSetCard}.
     */
    class QuestionSetListViewCell extends ListCell<QuestionSet> {
        @Override
        protected void updateItem(QuestionSet questionSet, boolean empty) {
            super.updateItem(questionSet, empty);

            if (empty || questionSet == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionSetCard(questionSet, getIndex() + 1).getRoot());
            }
        }
    }

}
