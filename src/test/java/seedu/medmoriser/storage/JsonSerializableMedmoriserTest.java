package seedu.medmoriser.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.medmoriser.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.commons.util.JsonUtil;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.testutil.TypicalQAndA;

public class JsonSerializableMedmoriserTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableMedmoriserTest");
    private static final Path TYPICAL_QUESTIONSETS_FILE = TEST_DATA_FOLDER
            .resolve("typicalQuestionSetsMedmoriser.json");
    private static final Path INVALID_QUESTIONSET_FILE = TEST_DATA_FOLDER
            .resolve("invalidQuestionSetMedmoriser.json");
    private static final Path DUPLICATE_QUESTIONSET_FILE = TEST_DATA_FOLDER
            .resolve("duplicateQuestionSetMedmoriser.json");

    @Test
    public void toModelType_typicalQuestionSetsFile_success() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTIONSETS_FILE,
                JsonSerializableMedmoriser.class).get();
        Medmoriser medmoriserFromFile = dataFromFile.toModelType();
        Medmoriser typicalQuestionSetsMedmoriser = TypicalQAndA.getTypicalMedmoriser();
        assertEquals(medmoriserFromFile, typicalQuestionSetsMedmoriser);
    }

    @Test
    public void toModelType_invalidQuestionSetFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTIONSET_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQuestionSets_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTIONSET_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMedmoriser.MESSAGE_DUPLICATE_QANDA,
                dataFromFile::toModelType);
    }

}
