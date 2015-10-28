package de.jgholland.directorymesh.testutilities;

import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;

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
    String temporaryDirectoryPathString;
    Path masterDirectoryPath;
    Path dataDirectoryPath;
    MasterDataDirectoryPaths masterDataDirectoryPaths;

    public TestDirectoryEnvironment() throws IOException {
        temporaryDirectoryPath = Files.createTempDirectory("directoryMesh");
        masterDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "master/");
        dataDirectoryPath = Paths.get(temporaryDirectoryPath.toString(), "data/");
        temporaryDirectoryPathString = temporaryDirectoryPath.toString();
        System.out.println("created " + masterDirectoryPath + "\n" + dataDirectoryPath);

    }

    public MasterDataDirectoryPaths getMasterDataDirectoryPaths() throws IOException {
        if (masterDataDirectoryPaths == null) {
            masterDataDirectoryPaths = new MasterDataDirectoryPaths(masterDirectoryPath, dataDirectoryPath);
        }
        return masterDataDirectoryPaths;
    }



    public void removeTestDirectories() throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(new File(temporaryDirectoryPathString));
        System.out.println("deleted " + temporaryDirectoryPathString);
    }
}
