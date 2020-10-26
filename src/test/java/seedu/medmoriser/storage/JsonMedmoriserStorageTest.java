package seedu.medmoriser.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION1;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION8;
import static seedu.medmoriser.testutil.TypicalQAndA.QUESTION9;
import static seedu.medmoriser.testutil.TypicalQAndA.getTypicalMedmoriser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.medmoriser.commons.exceptions.DataConversionException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.ReadOnlyMedmoriser;

public class JsonMedmoriserStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMedmoriserStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMedmoriser_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMedmoriser(null));
    }

    private java.util.Optional<ReadOnlyMedmoriser> readMedmoriser(String filePath) throws Exception {
        return new JsonMedmoriserStorage(Paths.get(filePath)).readMedmoriser(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMedmoriser("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMedmoriser("notJsonFormatMedmoriser.json"));
    }

    @Test
    public void readMedmoriser_invalidQAndAMedmoriser_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMedmoriser("invalidQAndAMedmoriser.json"));
    }

    @Test
    public void readMedmoriser_invalidAndValidQAndAMedmoriser_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readMedmoriser("invalidAndValidQAndAMedmoriser.json"));
    }

    @Test
    public void readAndSaveMedmoriser_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Medmoriser original = getTypicalMedmoriser();
        JsonMedmoriserStorage jsonMedmoriserStorage = new JsonMedmoriserStorage(filePath);

        // Save in new file and read back
        jsonMedmoriserStorage.saveMedmoriser(original, filePath);
        ReadOnlyMedmoriser readBack = jsonMedmoriserStorage.readMedmoriser(filePath).get();
        assertEquals(original, new Medmoriser(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addQAndA(QUESTION8);
        original.removeQAndA(QUESTION1);
        jsonMedmoriserStorage.saveMedmoriser(original, filePath);
        readBack = jsonMedmoriserStorage.readMedmoriser(filePath).get();
        assertEquals(original, new Medmoriser(readBack));

        // Save and read without specifying file path
        original.addQAndA(QUESTION9);
        jsonMedmoriserStorage.saveMedmoriser(original); // file path not specified
        readBack = jsonMedmoriserStorage.readMedmoriser().get(); // file path not specified
        assertEquals(original, new Medmoriser(readBack));

    }

    @Test
    public void saveMedmoriser_nullMedmoriser_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMedmoriser(null, "SomeFile.json"));
    }

    /**
     * Saves {@code medmoriser} at the specified {@code filePath}.
     */
    private void saveMedmoriser(ReadOnlyMedmoriser medmoriser, String filePath) {
        try {
            new JsonMedmoriserStorage(Paths.get(filePath))
                    .saveMedmoriser(medmoriser, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMedmoriser_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMedmoriser(new Medmoriser(), null));
    }
}
