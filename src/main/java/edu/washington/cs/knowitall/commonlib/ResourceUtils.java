package edu.washington.cs.knowitall.commonlib;

import java.io.*;

public class ResourceUtils {
    public static InputStream loadResourceFromClasspath(String name, Class<?> loader) {
        return loader.getClassLoader().getResourceAsStream(name);
    }

    public static InputStream loadResourceFromPackageRoot(String name, Class<?> loader) {
        return loader.getResourceAsStream("/" + name);
    }

    public static InputStream loadResourceFromPackage(String name, Class<?> loader) {
        return loader.getResourceAsStream(name);
    }

    public static InputStream loadResource(String name, Class<?> loader) throws FileNotFoundException {
        InputStream stream;

        stream = loadResourceFromPackageRoot(name, loader);
        if (stream != null) {
            return stream;
        }

        stream = loadResourceFromPackage(name, loader);
        if (stream != null) {
            return stream;
        }

        stream = loadResourceFromClasspath(name, loader);
        if (stream != null) {
            return stream;
        }

        // we could not find it
        throw new FileNotFoundException("Could not find resource '" + name + "' (null returned).");
    }
}
