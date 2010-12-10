package edu.washington.cs.knowit.commonlib;

import java.io.*;
import java.util.*;

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
    
    public static void pipe(Reader reader, Writer writer) throws IOException {
        pipe(reader, writer, 1024);
    }
    
    public static void pipe(Reader reader, Writer writer, int buffersize) throws IOException {
        char[] buffer = new char[1024];
        while (reader.read(buffer) != -1) {
            writer.write(buffer);
        }
    }
    
    public static ArrayList<File> Find(File path, Boolean recursive) {
        ArrayList<File> files = new ArrayList<File>();
        Find(files, path, recursive);
        return files;
    }
    
    private static void Find(List<File> files, File path, Boolean recursive) {
        if (path.isDirectory()) {
            // iterate over files
            for (File file : path.listFiles()) {
                if (recursive || !file.isDirectory()) {
                    Find(files, file, recursive);
                }
            }
        } else {
            // process the file
            files.add(path);
        }
    }
}
