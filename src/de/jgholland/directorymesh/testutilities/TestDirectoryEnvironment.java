package de.jgholland.directorymesh.testutilities;

import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by john on 2015-10-28.
 */
public class TestDirectoryEnvironment {

    Path temporaryDirectoryPath;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    String testResourcePath = "testresources";
    String masterSubdirectoryName = "master";
    String dataSubdirectoryName = "data";

    public TestDirectoryEnvironment(String testName) throws IOException {
        createNewTemporaryDirectory();
        copyTestResourceDirectoryIntoTemporaryDirectory(testName);
    }

    private void createNewTemporaryDirectory() throws IOException {
        temporaryDirectoryPath = Files.createTempDirectory("directoryMesh");
        System.out.printf("New temporary directory here: %s%n", temporaryDirectoryPath);
    }

    private void copyTestResourceDirectoryIntoTemporaryDirectory(String testName) throws IOException {

        File srcDir = new File(Paths.get(testResourcePath, testName).toString());
        File destDir = new File(temporaryDirectoryPath.toString());
        FileUtils.copyDirectory(srcDir, destDir);
    }

    public void removeTestDirectories() throws IOException {
        deleteTemporaryDirectoryRecursively();
    }

    private void deleteTemporaryDirectoryRecursively() throws IOException {
        File directory = new File(temporaryDirectoryPath.toString());
        org.apache.commons.io.FileUtils.deleteDirectory(directory);
    }

    public MasterDataDirectoryPaths getMasterDataDirectoryPaths() throws IOException {
        if (masterDataDirectoryPaths == null) {
            Path masterDirectoryPath = getPathFromDirectoryPathAndSubdirectoryString(temporaryDirectoryPath, masterSubdirectoryName);
            Path dataDirectoryPath = getPathFromDirectoryPathAndSubdirectoryString(temporaryDirectoryPath, dataSubdirectoryName);
            masterDataDirectoryPaths = new MasterDataDirectoryPaths(masterDirectoryPath, dataDirectoryPath);
        }
        return masterDataDirectoryPaths;
    }

    private Path getPathFromDirectoryPathAndSubdirectoryString(Path directory, String subdirectoryString) {
        return Paths.get(directory.toString(), subdirectoryString);
    }


}
