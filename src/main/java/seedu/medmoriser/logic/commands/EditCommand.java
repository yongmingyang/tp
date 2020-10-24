package seedu.medmoriser.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.medmoriser.model.Model.PREDICATE_SHOW_ALL_QANDA;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.medmoriser.commons.core.Messages;
import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.commons.util.CollectionUtil;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.qanda.Answer;
import seedu.medmoriser.model.qanda.Email;
import seedu.medmoriser.model.qanda.Phone;
import seedu.medmoriser.model.qanda.QAndA;
import seedu.medmoriser.model.qanda.Question;
import seedu.medmoriser.model.tag.Tag;

/**
 * Edits the details of an existing QAndA in the question bank.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the QAndA identified "
            + "by the index number used in the displayed QAndA list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_ANSWER + "ANSWER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUESTION + "What causes pimples? "
            + PREFIX_ANSWER + "triggered by androgen hormones and, in some cases, genetics";

    public static final String MESSAGE_EDIT_QANDA_SUCCESS = "Edited qAndA: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_QANDA = "This QAndA already exists in the question bank.";

    private final Index index;
    private final EditQAndADescriptor editQAndADescriptor;

    /**
     * @param index of the qAndA in the filtered qAndA list to edit
     * @param editQAndADescriptor details to edit the qAndA with
     */
    public EditCommand(Index index, EditQAndADescriptor editQAndADescriptor) {
        requireNonNull(index);
        requireNonNull(editQAndADescriptor);

        this.index = index;
        this.editQAndADescriptor = new EditQAndADescriptor(editQAndADescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (QuizCommand.getIsQuiz()) {
            throw new CommandException(Messages.MESSAGE_ONGOING_QUIZ);
        } else {
            List<QAndA> lastShownList = model.getFilteredQAndAList();
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_QANDA_DISPLAYED_INDEX);
            }

            QAndA qAndAToEdit = lastShownList.get(index.getZeroBased());
            QAndA editedQAndA = createEditedQAndA(qAndAToEdit, editQAndADescriptor);

            if (!qAndAToEdit.isSameQAndA(editedQAndA) && model.hasQAndA(editedQAndA)) {
                throw new CommandException(MESSAGE_DUPLICATE_QANDA);
            }

            model.setQAndA(qAndAToEdit, editedQAndA);
            model.updateFilteredQAndAList(PREDICATE_SHOW_ALL_QANDA);
            return new CommandResult(String.format(MESSAGE_EDIT_QANDA_SUCCESS, editedQAndA));
        }
    }

    /**
     * Creates and returns a {@code QAndA} with the details of {@code qAndAToEdit}
     * edited with {@code editQAndADescriptor}.
     */
    private static QAndA createEditedQAndA(QAndA qAndAToEdit,
                                           EditQAndADescriptor editQAndADescriptor) {
        assert qAndAToEdit != null;

        Question updatedQuestion = editQAndADescriptor.getQuestion().orElse(qAndAToEdit.getQuestion());
        Phone updatedPhone = editQAndADescriptor.getPhone().orElse(qAndAToEdit.getPhone());
        Email updatedEmail = editQAndADescriptor.getEmail().orElse(qAndAToEdit.getEmail());
        Answer updatedAnswer = editQAndADescriptor.getAnswer().orElse(qAndAToEdit.getAnswer());
        Set<Tag> updatedTags = editQAndADescriptor.getTags().orElse(qAndAToEdit.getTags());

        return new QAndA(updatedQuestion, updatedPhone, updatedEmail, updatedAnswer, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editQAndADescriptor.equals(e.editQAndADescriptor);
    }

    /**
     * Stores the details to edit the qAndA with. Each non-empty field value will replace the
     * corresponding field value of the qAndA.
     */
    public static class EditQAndADescriptor {
        private Question question;
        private Phone phone;
        private Email email;
        private Answer answer;
        private Set<Tag> tags;

        public EditQAndADescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditQAndADescriptor(EditQAndADescriptor toCopy) {
            setQuestion(toCopy.question);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAnswer(toCopy.answer);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, phone, email, answer, tags);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditQAndADescriptor)) {
                return false;
            }

            // state check
            EditQAndADescriptor e = (EditQAndADescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAnswer().equals(e.getAnswer())
                    && getTags().equals(e.getTags());
        }
    }
}
