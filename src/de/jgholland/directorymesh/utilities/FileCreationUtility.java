package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by john on 2015-10-28.
 */
public class FileCreationUtility {

    public static void fileCreationReporter(String string) {
        System.out.println(string);
    }

    public static void reportNewSoftLink(Path regularLocation, Path softLinkLocation) {
        fileCreationReporter("Created symbolic link " + regularLocation.toString() + " <- " + softLinkLocation.toString());
    }

    public static void createSymlink(Path realFileLocation, Path softLinkLocation) throws Exception {
        Files.createSymbolicLink(softLinkLocation, realFileLocation);
        reportNewSoftLink(realFileLocation, softLinkLocation);
    }

    public static void touchFile(Path path) throws Exception {
        createDirectory(path.getParent());
        Files.createFile(path);
        fileCreationReporter("Created regular file " + path);
    }

    public static void createDirectory(Path path) throws Exception {
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
            fileCreationReporter("Created directory " + path);
        }
    }

    public static void createLinkedDirectoryPair(Path regularDirectoryLocation, Path softLinkLocation) throws Exception {
        createDirectory(regularDirectoryLocation);
        createSymlink(regularDirectoryLocation, softLinkLocation);

    }

    public static void touchLinkedFilePair(Path realFileLocation, Path softLinkLocation) throws Exception {
        touchFile(realFileLocation);
        createSymlink(realFileLocation, softLinkLocation);
    }


}
