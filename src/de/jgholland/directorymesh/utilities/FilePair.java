package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by john on 2015-10-28.
 */
public class FilePair {
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    BasicFileAttributes masterBasicFileAttributes;
    BasicFileAttributes dataBasicFileAttributes;
    public Path masterPath;
    public Path dataPath;

    public FilePair(Path relativePathInDirectory, MasterDataDirectoryPaths masterDataDirectoryPaths) throws Exception {
        this.masterDataDirectoryPaths = masterDataDirectoryPaths;
        masterPath = masterDataDirectoryPaths.pathInMasterDirectory(relativePathInDirectory);
        dataPath = masterDataDirectoryPaths.pathInDataDirectory(relativePathInDirectory);
        masterBasicFileAttributes = Files.readAttributes(masterPath, BasicFileAttributes.class);
        dataBasicFileAttributes = Files.readAttributes(dataPath, BasicFileAttributes.class);
    }

    public boolean bothFilesAreReal() {
        return !masterBasicFileAttributes.isSymbolicLink() && !dataBasicFileAttributes.isSymbolicLink();
    }

    public boolean oneIsDirectoryAndOneIsRegular() {
        return (
                    (masterBasicFileAttributes.isRegularFile() && dataBasicFileAttributes.isDirectory())
                ||
                    (masterBasicFileAttributes.isDirectory() && dataBasicFileAttributes.isRegularFile()
                )
        );
    }

}
