package edu.washington.cs.knowit.commonlib;

import java.io.File;

public class FileUtils {
    public static String getExtension(File f) {
        String ext = null;
        
        String name = f.getName();
        int i = name.lastIndexOf('.');

        if (i > 0 &&  i < name.length() - 1) {
            ext = name.substring(i+1).toLowerCase();
        }
        
        return ext;
    }
}
