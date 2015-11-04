package de.jgholland.directorymesh.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by john on 2015-10-28.
 */
public class FileCreationUtility {

    public static void fileCreationReporter(String string) {
        System.out.println(string);
    }

    public static void reportNewSymink(Path regularLocation, Path softLinkLocation) {
        reportNewSymink(regularLocation.toString(), softLinkLocation.toString());
    }

    public static void reportNewSymink(String regularLocation, String softLinkLocation) {
        fileCreationReporter("Symbolic link " + regularLocation + " <- " + softLinkLocation);
    }

    public static void reportRemoveSymink(String softLinkLocation) {
        fileCreationReporter("Removing link " + softLinkLocation);
    }

    public static void reportRemoveSymink(Path softLinkLocation) {
        reportRemoveSymink(softLinkLocation.toString());
    }

    public static void createSymlink(Path realFileLocation, Path softLinkLocation) {
        try {
            Files.createSymbolicLink(softLinkLocation, realFileLocation);
        } catch (IOException e) {
            System.err.println(e);
        } catch (UnsupportedOperationException e) {
            // Some file systems do not support symbolic links.
            System.err.println(e);
        }
    }
    public static void removeSymlink(Path softLinkLocation) {
        try {
        BasicFileAttributes basicFileAttributes = getBasicFileAttributes(softLinkLocation);
        if (!basicFileAttributes.isSymbolicLink()) {
            throw new IOException("tried to delete a symlink which wasn't a symlink.");
        }
        Files.delete(softLinkLocation);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    static BasicFileAttributes getBasicFileAttributes(Path path) {

        return getBasicFileAttributes(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public static BasicFileAttributes getBasicFileAttributesOfEndFile(Path path) {

        return getBasicFileAttributes(path, new LinkOption[]{}); // Follows links
    }

    public static BasicFileAttributes getBasicFileAttributes(Path path, LinkOption[] options) {

        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = Files.readAttributes(
                    path,
                    BasicFileAttributes.class,
                    options);
        } catch (IOException e) {
            basicFileAttributes = null; // The file doesn't exist
        }
        return basicFileAttributes;
    }
}
