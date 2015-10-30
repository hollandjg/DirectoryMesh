package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by john on 2015-10-28.
 */
public class FilePair {
    Path relativePathInDirectory;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    BasicFileAttributes master;
    BasicFileAttributes data;
    public Path masterPath;
    public Path dataPath;
    boolean masterExists;
    boolean dataExists;

    public FilePair(Path relativePathInDirectory, MasterDataDirectoryPaths masterDataDirectoryPaths) throws Exception {
        this.relativePathInDirectory = relativePathInDirectory;
        this.masterDataDirectoryPaths = masterDataDirectoryPaths;
        initialisePathVariables(relativePathInDirectory, masterDataDirectoryPaths);
        getFileAttributes();
    }

    private void getFileAttributes() throws Exception {

        master = getBasicFileAttributes(masterPath);
        masterExists = fileExistsOrIsLink(master);

        data = getBasicFileAttributes(dataPath);
        dataExists = fileExistsOrIsLink(data);
    }

    private boolean fileExistsOrIsLink(BasicFileAttributes basicFileAttributes) {
        return basicFileAttributes != null;
    }

    private boolean masterIsSymbolicAndPointsToData() throws Exception {
        return master.isSymbolicLink() && doesMasterPointToData();
    }

    private boolean doesMasterPointToData() throws Exception {
        return dataPath == Files.readSymbolicLink(masterPath);
    }

    private void initialisePathVariables(Path relativePathInDirectory, MasterDataDirectoryPaths masterDataDirectoryPaths) {
        masterPath = masterDataDirectoryPaths.pathInMasterDirectory(relativePathInDirectory);
        dataPath = masterDataDirectoryPaths.pathInDataDirectory(relativePathInDirectory);
    }

    private BasicFileAttributes getBasicFileAttributes(Path path) {
        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = Files.readAttributes(
                    path,
                    BasicFileAttributes.class,
                    LinkOption.NOFOLLOW_LINKS);
        } catch (java.io.IOException e) {
            basicFileAttributes = null; // The file doesn't exist
        }
        return basicFileAttributes;
    }

    public String getRelativePathWithinDirectories() {
        return this.relativePathInDirectory.toString();
    }

    public boolean neitherFileExists() {
        return !masterExists && !dataExists;
    }

    public boolean masterExistsButDataDoesnt() {
        return masterExists && !dataExists;
    }

    public boolean dataExistsButMasterDoesnt() {
        return !masterExists && dataExists;
    }

    public boolean bothFilesAreReal() {
        return !master.isSymbolicLink() && !data.isSymbolicLink();
    }

    public boolean masterIsABackLinkToAMissingFile() throws Exception {
        return (!dataExists && masterIsSymbolicAndPointsToData());
    }

    public boolean oneIsDirectoryAndOneIsRegular() {
        return ((master.isRegularFile() && data.isDirectory()) || (master.isDirectory() && data.isRegularFile()));
    }

}
