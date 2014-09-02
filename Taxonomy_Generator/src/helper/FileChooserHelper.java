/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author radmin
 */
public final class FileChooserHelper {
    public static FileFilter OpenFileChooserFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() /*|| f.getName().startsWith("!")*/ || f.getName().matches("^[a-zA-Z0-9_-](.txt|.data)?$");
            }

            @Override
            public String getDescription() {
                return "Data files (.data/.txt)";
            }
        };
    }
    
    public static FileFilter SaveFileChooserFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().matches("^[a-zA-Z0-9_-!].taxonomy$");
            }

            @Override
            public String getDescription() {
                return "Taxonomy files (.taxonomy)";
            }
        };
    }
}
