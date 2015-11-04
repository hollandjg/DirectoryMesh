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

    public static void reportNewSymink(Path regularLocation, Path softLinkLocation) {
        reportNewSymink(regularLocation.toString(), softLinkLocation.toString());
    }

    public static void reportNewSymink(String regularLocation, String softLinkLocation) {
        fileCreationReporter("Created symbolic link " + regularLocation + " <- " + softLinkLocation);
    }


    public static void createSymlink(Path realFileLocation, Path softLinkLocation) throws Exception {
        Files.createSymbolicLink(softLinkLocation, realFileLocation);
        reportNewSymink(realFileLocation, softLinkLocation);
    }

}
