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
import seedu.medmoriser.model.ReadOnlyMedmoriser;
import seedu.medmoriser.model.ReadOnlyUserPrefs;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.testutil.QAndABuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullQAndA_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_qAndAAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingQAndAAdded modelStub = new ModelStubAcceptingQAndAAdded();
        QAndA validQAndA = new QAndABuilder().build();

        CommandResult commandResult = new AddCommand(validQAndA).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validQAndA), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQAndA), modelStub.qAndAsAdded);
    }

    @Test
    public void execute_duplicateQAndA_throwsCommandException() {
        QAndA validQAndA = new QAndABuilder().build();
        AddCommand addCommand = new AddCommand(validQAndA);
        ModelStub modelStub = new ModelStubWithQAndA(validQAndA);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_QANDA, () ->
            addCommand.execute(modelStub)
        );
    }

    @Test
    public void execute_answersHidden_successAndShowsAnswers() throws Exception {
        ListCommand listCommand = new ListCommand(false);
        ModelStubAcceptingQAndAAdded modelStub = new ModelStubAcceptingQAndAAdded();
        QAndA validQAndA = new QAndABuilder().build();

        CommandResult commandResult = new AddCommand(validQAndA).execute(modelStub);
        assertTrue(commandResult.isAnswerDisplayed());
    }

    @Test
    public void equals() {
        QAndA alice = new QAndABuilder().withQuestion("Alice").build();
        QAndA bob = new QAndABuilder().withQuestion("Bob").build();
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

        // different qAndA -> returns false
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
            throw new AssertionError("This method"
                    + " should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method "
                    + "should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMedmoriserFilePath() {
            throw new AssertionError("This method "
                    + "should not be called.");
        }

        @Override
        public void setMedmoriserFilePath(Path medmoriserFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQAndA(QAndA qAndA) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMedmoriser(ReadOnlyMedmoriser medmoriser) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMedmoriser getMedmoriser() {
            throw new AssertionError("This method should"
                    + " not be called.");
        }

        @Override
        public boolean hasQAndA(QAndA qAndA) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteQAndA(QAndA target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQAndA(QAndA target, QAndA editedQAndA) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<QAndA> getFilteredQAndAList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredQAndAList(Predicate<QAndA> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single qAndA.
     */
    private class ModelStubWithQAndA extends ModelStub {
        private final QAndA qAndA;

        ModelStubWithQAndA(QAndA qAndA) {
            requireNonNull(qAndA);
            this.qAndA = qAndA;
        }

        @Override
        public boolean hasQAndA(QAndA qAndA) {
            requireNonNull(qAndA);
            return this.qAndA.isSameQAndA(qAndA);
        }
    }

    /**
     * A Model stub that always accept the qAndA being added.
     */
    private class ModelStubAcceptingQAndAAdded extends ModelStub {
        final ArrayList<QAndA> qAndAsAdded = new ArrayList<>();

        @Override
        public boolean hasQAndA(QAndA qAndA) {
            requireNonNull(qAndA);
            return qAndAsAdded.stream().anyMatch(qAndA::isSameQAndA);
        }

        @Override
        public void addQAndA(QAndA qAndA) {
            requireNonNull(qAndA);
            qAndAsAdded.add(qAndA);
        }

        @Override
        public ReadOnlyMedmoriser getMedmoriser() {
            return new Medmoriser();
        }
    }

}
