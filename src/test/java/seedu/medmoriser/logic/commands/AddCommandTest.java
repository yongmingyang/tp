package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.medmoriser.commons.core.GuiSettings;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ReadOnlyAddressBook;
import seedu.medmoriser.model.ReadOnlyUserPrefs;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullQuestionSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_questionSetAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingQuestionSetAdded modelStub = new ModelStubAcceptingQuestionSetAdded();
        QuestionSet validQuestionSet = new QuestionSetBuilder().build();

        CommandResult commandResult = new AddCommand(validQuestionSet).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validQuestionSet), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQuestionSet), modelStub.questionSetsAdded);
    }

    @Test
    public void execute_duplicateQuestionSet_throwsCommandException() {
        QuestionSet validQuestionSet = new QuestionSetBuilder().build();
        AddCommand addCommand = new AddCommand(validQuestionSet);
        ModelStub modelStub = new ModelStubWithQuestionSet(validQuestionSet);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_QUESTIONSET, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        QuestionSet alice = new QuestionSetBuilder().withName("Alice").build();
        QuestionSet bob = new QuestionSetBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different questionSet -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuestionSet(QuestionSet questionSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasQuestionSet(QuestionSet questionSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteQuestionSet(QuestionSet target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuestionSet(QuestionSet target, QuestionSet editedQuestionSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<QuestionSet> getFilteredQuestionSetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredQuestionSetList(Predicate<QuestionSet> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single questionSet.
     */
    private class ModelStubWithQuestionSet extends ModelStub {
        private final QuestionSet questionSet;

        ModelStubWithQuestionSet(QuestionSet questionSet) {
            requireNonNull(questionSet);
            this.questionSet = questionSet;
        }

        @Override
        public boolean hasQuestionSet(QuestionSet questionSet) {
            requireNonNull(questionSet);
            return this.questionSet.isSameQuestionSet(questionSet);
        }
    }

    /**
     * A Model stub that always accept the questionSet being added.
     */
    private class ModelStubAcceptingQuestionSetAdded extends ModelStub {
        final ArrayList<QuestionSet> questionSetsAdded = new ArrayList<>();

        @Override
        public boolean hasQuestionSet(QuestionSet questionSet) {
            requireNonNull(questionSet);
            return questionSetsAdded.stream().anyMatch(questionSet::isSameQuestionSet);
        }

        @Override
        public void addQuestionSet(QuestionSet questionSet) {
            requireNonNull(questionSet);
            questionSetsAdded.add(questionSet);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new Medmoriser();
        }
    }

}
