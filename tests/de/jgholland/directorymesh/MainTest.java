package de.jgholland.directorymesh;

import org.junit.*;

import java.io.File;
import java.nio.file.*;

/**
 * Created by john on 2015-10-26.
 */
public class MainTest {

    Path temporaryDirectoryPath;
    Path masterDirectoryPath;
    Path dataDirectoryPath;
    String temporaryDirectoryPathString;
    String masterDirectoryPathString;
    String dataDirectoryPathString;

    @org.junit.Before
    public void setUp() throws Exception {
        temporaryDirectoryPath = java.nio.file.Files.createTempDirectory("directoryMesh");
        masterDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "master/");
        dataDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "data/");
        temporaryDirectoryPathString = temporaryDirectoryPath.toString();
        masterDirectoryPathString = masterDirectoryPath.toString();
        dataDirectoryPathString = dataDirectoryPath.toString();
        System.out.println("created " + masterDirectoryPath + "\n" + dataDirectoryPath);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        org.apache.commons.io.FileUtils.deleteDirectory(new File(masterDirectoryPath.toString()));
        org.apache.commons.io.FileUtils.deleteDirectory(new File(dataDirectoryPath.toString()));
        System.out.println("deleted " + masterDirectoryPath + "\n" + dataDirectoryPath);
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


    public void touchLink(Path regularFileLocation, Path softLinkLocation) throws Exception {
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

    public void fileCreationReporter(String string) {
        String newstring = string.replaceAll(temporaryDirectoryPathString, "");
        System.out.println(newstring);
    }

    public void reportNewSoftLink(Path regularLocation, Path softLinkLocation) {
        fileCreationReporter("Created symbolic link " + regularLocation.toString() + " <- " + softLinkLocation.toString());
    }

    @Test
    public void testFilesystemCreation() throws Exception {
        touchFile(pathInDataDirectory("datafile0"));
        touchFile(pathInDataDirectory("datafile1"));
        touchFile(pathInDataDirectory("commonDir/datafile2"));
        createDirectory(pathInMasterDirectory("commonDir/"));
        createDirectory(pathInMasterDirectory("dirOnlyInMaster/datafile3"));
        touchLink(pathInDataDirectory("datafile4"), pathInMasterDirectory("datafile4"));
    }

    @Test
    public void testMain() throws Exception {
        de.jgholland.directorymesh.Main.main(new String[] {"-nq", masterDirectoryPathString, dataDirectoryPathString});
        de.jgholland.directorymesh.Main.main(new String[] {"-q",  masterDirectoryPathString, dataDirectoryPathString});
        de.jgholland.directorymesh.Main.main(new String[] {"-n",  masterDirectoryPathString, dataDirectoryPathString});

    }
}
