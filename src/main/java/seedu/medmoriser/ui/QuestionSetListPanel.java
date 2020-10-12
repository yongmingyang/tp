package seedu.medmoriser.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.medmoriser.commons.core.LogsCenter;
import seedu.medmoriser.model.qanda.QAndA;

/**
 * Panel containing the list of questionSet.
 */
public class QuestionSetListPanel extends UiPart<Region> {
    private static final String FXML = "QuestionSetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionSetListPanel.class);

    @FXML
    private ListView<QAndA> questionSetListView;

    /**
     * Creates a {@code QuestionSetListPanel} with the given {@code ObservableList}.
     */
    public QuestionSetListPanel(ObservableList<QAndA> qAndAList) {
        super(FXML);
        questionSetListView.setItems(qAndAList);
        questionSetListView.setCellFactory(listView -> new QuestionSetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QuestionSet} using a {@code QuestionSetCard}.
     */
    class QuestionSetListViewCell extends ListCell<QAndA> {
        @Override
        protected void updateItem(QAndA qAndA, boolean empty) {
            super.updateItem(qAndA, empty);

            if (empty || qAndA == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionSetCard(qAndA, getIndex() + 1).getRoot());
            }
        }
    }

}
