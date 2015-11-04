package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by john on 2015-10-28.
 */
public class FilePair {
    public Path masterPath;
    public Path dataPath;
    Path relativePathInDirectory;
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    BasicFileAttributes master;
    BasicFileAttributes data;
    boolean masterExists;
    boolean dataExists;

    public FilePair(Path relativePathInDirectory, MasterDataDirectoryPaths masterDataDirectoryPaths) throws Exception {

        this.relativePathInDirectory = relativePathInDirectory;
        this.masterDataDirectoryPaths = masterDataDirectoryPaths;
        initialisePathVariables(relativePathInDirectory, masterDataDirectoryPaths);
        getFileAttributes();
    }

    private void initialisePathVariables(
            Path relativePathInDirectory,
            MasterDataDirectoryPaths masterDataDirectoryPaths) {

        masterPath = masterDataDirectoryPaths.pathInMasterDirectory(relativePathInDirectory);
        dataPath = masterDataDirectoryPaths.pathInDataDirectory(relativePathInDirectory);
    }

    private void getFileAttributes() throws Exception {

        master = getBasicFileAttributes(masterPath);
        masterExists = fileExistsOrIsLink(master);
        data = getBasicFileAttributes(dataPath);
        dataExists = fileExistsOrIsLink(data);
    }

    private BasicFileAttributes getBasicFileAttributes(Path path) {

        return getBasicFileAttributes(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    private BasicFileAttributes getBasicFileAttributesOfEndFile(Path path) {

        return getBasicFileAttributes(path, new LinkOption[]{}); // Follows links
    }

    private BasicFileAttributes getBasicFileAttributes(Path path, LinkOption[] options) {

        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = Files.readAttributes(
                    path,
                    BasicFileAttributes.class,
                    options);
        } catch (java.io.IOException e) {
            basicFileAttributes = null; // The file doesn't exist
        }
        return basicFileAttributes;
    }

    public boolean fileExistsOrIsLink(BasicFileAttributes basicFileAttributes) {

        return basicFileAttributes != null;
    }

    public boolean masterIsSymbolicAndPointsToData() throws Exception {

        return master.isSymbolicLink() && masterPointsToData();
    }

    public boolean bothExistAndMasterIsNotABackLink() throws Exception {

        return masterExists && dataExists && !masterIsSymbolicAndPointsToData();
    }

    public boolean masterPointsToData() throws Exception {

        Path symlinkTarget = Files.readSymbolicLink(masterPath);
        Path masterLinkTarget = masterPath.resolveSibling(symlinkTarget).normalize();
        Path normalizedDataPath = dataPath.normalize();
        boolean pathsMatch = normalizedDataPath.equals(masterLinkTarget);
        return pathsMatch;
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

    public boolean masterIsABackLinkToAMissingFile() throws Exception {

        return (!dataExists && masterIsSymbolicAndPointsToData());
    }

    public boolean masterIsABackLinkToTheCorrectDataFile() throws Exception {

        return (dataExists && masterIsSymbolicAndPointsToData());
    }

    public boolean oneIsDirectoryAndOneIsRegular() {

        return ((master.isRegularFile() && data.isDirectory()) || (master.isDirectory() && data.isRegularFile()));
    }

    public boolean bothAreDirectories() {

        return data.isDirectory() && master.isDirectory();
    }

    public boolean dataIsDirectoryOrLinkToDirectory() throws Exception {

        BasicFileAttributes basicFileAttributesOfTarget = getBasicFileAttributesOfEndFile(dataPath);
        return basicFileAttributesOfTarget.isDirectory();
    }

    public String abbreviatedMasterPath() {
        return "[master]/" + relativePathInDirectory;
    }

    public String abbreviatedDataPath() {
        return "[data]/" + relativePathInDirectory;
    }
}
