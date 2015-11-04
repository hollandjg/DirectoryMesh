package de.jgholland.directorymesh.utilities;

import java.io.IOException;
import java.nio.file.Files;
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

    public FilePair(Path relativePathInDirectory, MasterDataDirectoryPaths masterDataDirectoryPaths) {

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

    private void getFileAttributes() {

        data = FileCreationUtility.getBasicFileAttributes(dataPath);
        master = FileCreationUtility.getBasicFileAttributes(masterPath);
        masterExists = fileExistsOrIsLink(master);
        dataExists = fileExistsOrIsLink(data);

    }

    public boolean fileExistsOrIsLink(BasicFileAttributes basicFileAttributes) {

        return basicFileAttributes != null;
    }

    public boolean masterIsSymbolicAndPointsToData() {

        return masterExists && master.isSymbolicLink() && masterPointsToData();
    }

    public boolean bothExistAndMasterIsNotABackLink() {

        return masterExists && dataExists && !masterIsSymbolicAndPointsToData();
    }

    public boolean masterPointsToData() {
        Path symlinkTarget;
        try {
            symlinkTarget = Files.readSymbolicLink(masterPath);
        } catch (IOException e) {
            return false;
        }
        Path masterLinkTarget = getAbsoluteMasterSymlinkTarget(symlinkTarget);
        Path normalizedDataPath = dataPath.normalize();
        boolean pathsMatch = normalizedDataPath.equals(masterLinkTarget);
        return pathsMatch;
    }

    Path getAbsoluteMasterSymlinkTarget(Path symlinkTarget) {
        return masterPath.resolveSibling(symlinkTarget).normalize();
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

    public boolean masterIsABackLinkToAMissingFile() {

        return (!dataExists && masterIsSymbolicAndPointsToData());
    }

    public boolean masterIsABackLinkToTheCorrectDataFile() {

        return (dataExists && masterIsSymbolicAndPointsToData());
    }

    public boolean oneIsDirectoryAndOneIsRegular() {

        return ((master.isRegularFile() && data.isDirectory()) || (master.isDirectory() && data.isRegularFile()));
    }

    public boolean bothAreDirectories() {

        return data.isDirectory() && master.isDirectory();
    }

    public boolean dataIsDirectoryOrLinkToDirectory() {

        BasicFileAttributes basicFileAttributesOfTarget = FileCreationUtility.getBasicFileAttributesOfEndFile(dataPath);
        return basicFileAttributesOfTarget.isDirectory();
    }

    public boolean masterIsABackLinkPointingSomewhereElseInTheDataDirectory() {
        if (!masterExists || !master.isSymbolicLink())
            return false;
        boolean masterWithinDataTree = masterPointsWithinDataTree();
        boolean masterDoesntPointAtCorrectDataFile = !masterPointsToData();
        return masterWithinDataTree && masterDoesntPointAtCorrectDataFile;
    }

    public boolean masterPointsWithinDataTree() {
        Path symlinkTarget;
        try {
            symlinkTarget = Files.readSymbolicLink(masterPath);
        } catch (IOException e) {
            return false;
        }
        Path resolvedSymlinkTarget = getAbsoluteMasterSymlinkTarget(symlinkTarget);
        boolean linkTargetIsChildOfData = resolvedSymlinkTarget.startsWith(this.masterDataDirectoryPaths.dataDirectoryPath);
        return linkTargetIsChildOfData;
    }

    public String abbreviatedMasterPath() {
        return "[master]/" + relativePathInDirectory;
    }

    public String abbreviatedDataPath() {
        return "[data]/" + relativePathInDirectory;
    }
}
