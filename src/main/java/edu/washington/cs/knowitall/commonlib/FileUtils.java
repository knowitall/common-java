package edu.washington.cs.knowitall.commonlib;

import java.io.*;
import java.util.*;

public class FileUtils {
	/***
	 * Get the text after the final dot (.) in the file name.
	 * @param f The file to get the extension of.
	 * @return The extension of File f.
	 */
    public static String getExtension(File f) {
        String ext = null;
        
        String name = f.getName();
        int i = name.lastIndexOf('.');

        if (i > 0 &&  i < name.length() - 1) {
            ext = name.substring(i+1).toLowerCase();
        }
        
        return ext;
    }
    
    /***
     * Writes all lines read from the reader.
     * @param reader The source reader
     * @param writer The destination writer
     * @throws IOException
     */
    public static void pipe(Reader reader, Writer writer) throws IOException {
        pipe(reader, writer, 4092);
    }
    
    /***
     *  Writes all lines read from the reader.
     * @param reader the source reader
     * @param writer the destination writer
     * @param buffersize size of the buffer to use
     * @throws IOException
     */
    public static void pipe(Reader reader, Writer writer, int buffersize) throws IOException {
        char[] buffer = new char[buffersize];
        while (reader.read(buffer) != -1) {
            writer.write(buffer);
        }
    }
    
    /***
     * Writes all lines read from the reader.
     * @param is The input stream
     * @param os The output stream
     * @throws IOException
     */
    public static void pipe(InputStream is, OutputStream os) throws IOException {
        pipe(is, os, 4092);
    }
    
    /***
     *  Writes all lines read from the reader.
     * @param is The input stream
     * @param os The output stream
     * @param buffersize size of the buffer to use
     * @throws IOException
     */
    public static void pipe(InputStream is, OutputStream os, int buffersize) throws IOException {
        byte[] buffer = new byte[buffersize];
        while (is.read(buffer) != -1) {
            os.write(buffer);
        }
    }
    
    /***
     * Return all files beneath path.
     * @param path the path to search
     * @param recursive iff true, search subdirectories too.
     * @return
     */
    public static ArrayList<File> Find(File path, Boolean recursive) {
        ArrayList<File> files = new ArrayList<File>();
        Find(files, path, recursive);
        return files;
    }
    
    /***
     * A private helper function for building the results for public Find.
     * @param files
     * @param path
     * @param recursive
     */
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
