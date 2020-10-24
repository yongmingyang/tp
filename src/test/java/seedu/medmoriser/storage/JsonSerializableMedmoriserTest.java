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
    private static final Path TYPICAL_QANDAS_FILE = TEST_DATA_FOLDER
            .resolve("typicalQAndAsMedmoriser.json");
    private static final Path INVALID_QANDA_FILE = TEST_DATA_FOLDER
            .resolve("invalidQAndAMedmoriser.json");
    private static final Path DUPLICATE_QANDA_FILE = TEST_DATA_FOLDER
            .resolve("duplicateQAndAMedmoriser.json");

    @Test
    public void toModelType_typicalQAndAsFile_success() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(TYPICAL_QANDAS_FILE,
                JsonSerializableMedmoriser.class).get();
        Medmoriser medmoriserFromFile = dataFromFile.toModelType();
        Medmoriser typicalQAndAsMedmoriser = TypicalQAndA.getTypicalMedmoriser();
        assertEquals(medmoriserFromFile, typicalQAndAsMedmoriser);
    }

    @Test
    public void toModelType_invalidQAndAFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(INVALID_QANDA_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQAndAs_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QANDA_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMedmoriser.MESSAGE_DUPLICATE_QANDA,
                dataFromFile::toModelType);
    }

}
