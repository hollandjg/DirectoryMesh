package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.jgholland.directorymesh.utilities.FileCreationUtility;

/**
 * Created by john on 2015-10-28.
 */
public class FileUtilities {
    FileCreationReporter fileCreationReporter;
    MasterDataDirectoryPaths masterDataDirectoryPaths;

    public FileUtilities(MasterDataDirectoryPaths masterDataDirectoryPaths) {
        this.masterDataDirectoryPaths = masterDataDirectoryPaths;
        this.fileCreationReporter = new FileCreationReporter(masterDataDirectoryPaths);
    }


    public void touchFileInMaster(Path path) throws Exception {
        FileCreationUtility.touchFile(masterDataDirectoryPaths.pathInMasterDirectory(path));
    }

    public void touchFileInMaster(String path) throws Exception {
        touchFileInMaster(Paths.get(path));
    }

    public void touchFilesInMasterAndData(String filename) throws Exception {
        FileCreationUtility.touchFile(masterDataDirectoryPaths.pathInDataDirectory(filename));
        FileCreationUtility.touchFile(masterDataDirectoryPaths.pathInMasterDirectory(filename));
    }


    public void createDirectoryInMaster(Path path) throws Exception {
        createDirectory(masterDataDirectoryPaths.pathInMasterDirectory(path));
    }

    public void createDirectoryInMaster(String path) throws Exception {
        createDirectoryInMaster(Paths.get(path));
    }




}
