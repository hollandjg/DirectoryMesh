package de.jgholland.directorymesh.testutilities;

import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
    String testName;

    public TestDirectoryEnvironment(String testName) throws IOException {
        this.testName = testName;
        createNewTemporaryDirectory();
        copyTestResourceDirectoryIntoTemporaryDirectory();
        initialiseMasterDataDirectoryPaths();
    }

    private void createNewTemporaryDirectory() throws IOException {
        temporaryDirectoryPath = Files.createTempDirectory("directoryMesh");
        System.out.printf("New temporary directory here: %s%n", temporaryDirectoryPath);
    }

    private void copyTestResourceDirectoryIntoTemporaryDirectory() throws IOException {
        String srcDir = Paths.get(testResourcePath, testName).toString();
        String destDir = temporaryDirectoryPath.toString();
        copyDirectoriesPreservingLinks(srcDir, destDir);

    }

    private void copyDirectoriesPreservingLinks(String srcDir, String destDir) {
        String rsyncCommand = "rsync -a " + srcDir + " " + destDir;
        try {
            Process rsyncProcess = Runtime.getRuntime().exec(rsyncCommand);

            BufferedReader rsyncReader = new BufferedReader(
                    new InputStreamReader(rsyncProcess.getInputStream()));
            String line;
            while ((line = rsyncReader.readLine()) != null) {
                System.out.println(line);
            }

            rsyncReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            initialiseMasterDataDirectoryPaths();
        }
        return masterDataDirectoryPaths;
    }

    private void initialiseMasterDataDirectoryPaths() {
        Path masterDirectoryPath = getPathFromDirectoryPathAndSubdirectoryString(temporaryDirectoryPath, testName + "/" + masterSubdirectoryName);
        Path dataDirectoryPath = getPathFromDirectoryPathAndSubdirectoryString(temporaryDirectoryPath, testName + "/" + dataSubdirectoryName);
        masterDataDirectoryPaths = new MasterDataDirectoryPaths(masterDirectoryPath, dataDirectoryPath);
    }

    private Path getPathFromDirectoryPathAndSubdirectoryString(Path directory, String subdirectoryString) {
        return Paths.get(directory.toString(), subdirectoryString);
    }


}
