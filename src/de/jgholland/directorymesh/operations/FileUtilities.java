package de.jgholland.directorymesh.operations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by john on 2015-10-28.
 */
public class FileUtilities {

    Path masterDirectoryPath;
    Path dataDirectoryPath;
    public String masterDirectoryPathString;
    public String dataDirectoryPathString;

    public FileUtilities(Path masterDirectoryPath, Path dataDirectoryPath) {
        this.masterDirectoryPath = masterDirectoryPath;
        this.dataDirectoryPath = dataDirectoryPath;
        masterDirectoryPathString = masterDirectoryPath.toString();
        dataDirectoryPathString = dataDirectoryPath.toString();
    }

    public Path pathInMasterDirectory(String path) {
        return Paths.get(masterDirectoryPath + "/" + path);
    }

    public Path pathInDataDirectory(String path) {
        return Paths.get(dataDirectoryPath + "/" + path);
    }

    public void touchFile(Path path) throws Exception {
        createDirectory(path.getParent());
        Files.createFile(path);
        fileCreationReporter("Created regular file " + path);
    }


    public void touchWithLink(Path regularFileLocation, Path softLinkLocation) throws Exception {
        touchFile(regularFileLocation);
        Files.createSymbolicLink(softLinkLocation, regularFileLocation);
        reportNewSoftLink(regularFileLocation, softLinkLocation);
    }

    public void createDirectory(Path path) throws Exception {
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
            fileCreationReporter("Created directory " + path);
        }
    }

    public void createLinkedDirectory(Path regularDirectoryLocation, Path softLinkLocation) throws Exception {
        createDirectory(regularDirectoryLocation);
        Files.createSymbolicLink(softLinkLocation, regularDirectoryLocation);
        reportNewSoftLink(regularDirectoryLocation, softLinkLocation);
    }
    public void reportNewSoftLink(Path regularLocation, Path softLinkLocation) {
        fileCreationReporter("Created symbolic link " + regularLocation.toString() + " <- " + softLinkLocation.toString());
    }
    public void fileCreationReporter(String string) {
        String newstring = string.replaceAll(masterDirectoryPathString, "[master]").replaceAll(dataDirectoryPathString, "[data]");
        System.out.println(newstring);
    }
}
