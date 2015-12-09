package interpreter.utils;

import javax.swing.filechooser.FileSystemView;

public class FileUtils {

    public static String getDocumentsPath() {
        return FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    }
}
