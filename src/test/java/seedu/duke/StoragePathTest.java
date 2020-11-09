package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.InvalidStorageFileExtensionException;
import seedu.duke.exceptions.InvalidStorageFilePathException;
import seedu.duke.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@author k-walter
class StoragePathTest {
    static class Rule<T extends Throwable> {
        final String path;
        final Class<T> exception;
        final String description;

        Rule(String path, Class<T> exception, String description) {
            this.path = path;
            this.exception = exception;
            this.description = description;
        }
    }

    static Rule[] rules = new Rule[]{
        new Rule("       ", InvalidStorageFilePathException.class, "Allowed blank file path/names"),
        new Rule("json", InvalidStorageFileExtensionException.class, "Must specify file extension"),
        new Rule("data/test.txt", InvalidStorageFileExtensionException.class, "Allowed non JSON files"),
    };

    @Test
    public void fullFileName() {
        for (Rule r : rules) {
            assertThrows(r.exception, () -> {
                Storage s = new Storage(r.path);
            }, r.description);
        }
    }
}
