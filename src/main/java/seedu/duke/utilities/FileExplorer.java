package seedu.duke.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * This class helps to open a local file.
 */
//@@author Wu-Haitao
public class FileExplorer {
    String filePath;

    public FileExplorer(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Opens the file according to the given file path.
     */
    public void openFile() throws IOException {
        File file = new File(filePath);
        Desktop.getDesktop().open(file);
    }
}
