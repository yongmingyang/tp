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

    public void setAnswerView(boolean isAnswerDisplayed) {
        questionSetListView.setCellFactory(listView -> {
            QuestionSetListViewCell temp = new QuestionSetListViewCell();
            temp.setAnswerDisplayed(isAnswerDisplayed);
            return temp;
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QuestionSet} using a {@code QuestionSetCard}.
     */
    public class QuestionSetListViewCell extends ListCell<QAndA> {
        private boolean isAnswerDisplayed;

        public QuestionSetListViewCell() {
            super();
            this.isAnswerDisplayed = true;
        }

        public void setAnswerDisplayed(boolean isAnswerDisplayed) {
            this.isAnswerDisplayed = isAnswerDisplayed;
        }

        @Override
        protected void updateItem(QAndA qAndA, boolean empty) {
            super.updateItem(qAndA, empty);

            if (empty || qAndA == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (qAndA.getIsQuiz()) {
                    setGraphic(new QuizCard(qAndA, getIndex() + 1, false).getRoot());
                } else if (isAnswerDisplayed) {
                    setGraphic(new QuestionSetCard(qAndA, getIndex() + 1, true).getRoot());
                } else {
                    setGraphic(new QuestionSetCard(qAndA, getIndex() + 1, false).getRoot());
                }
            }
        }
    }

}
