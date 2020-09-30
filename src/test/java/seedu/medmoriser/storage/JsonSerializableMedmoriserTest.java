package seedu.medmoriser.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.medmoriser.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.commons.util.JsonUtil;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.testutil.TypicalQuestionSet;

public class JsonSerializableMedmoriserTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_QUESTIONSETS_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionSetsAddressBook.json");
    private static final Path INVALID_QUESTIONSET_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionSetAddressBook.json");
    private static final Path DUPLICATE_QUESTIONSET_FILE = TEST_DATA_FOLDER.resolve("duplicateQuestionSetAddressBook.json");

    @Test
    public void toModelType_typicalQuestionSetsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTIONSETS_FILE,
                JsonSerializableAddressBook.class).get();
        Medmoriser medmoriserFromFile = dataFromFile.toModelType();
        Medmoriser typicalQuestionSetsMedmoriser = TypicalQuestionSet.getTypicalAddressBook();
        assertEquals(medmoriserFromFile, typicalQuestionSetsMedmoriser);
    }

    @Test
    public void toModelType_invalidQuestionSetFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTIONSET_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQuestionSets_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTIONSET_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_QUESTIONSET,
                dataFromFile::toModelType);
    }

}
