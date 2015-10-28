package de.jgholland.directorymesh.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by john on 2015-10-28.
 */
public class MasterDataDirectoryPaths {

    Path masterDirectoryPath;
    Path dataDirectoryPath;
    public String masterDirectoryPathString;
    public String dataDirectoryPathString;

    public MasterDataDirectoryPaths(Path masterDirectoryPath, Path dataDirectoryPath) {
        this.masterDirectoryPath = masterDirectoryPath;
        this.dataDirectoryPath = dataDirectoryPath;
        masterDirectoryPathString = masterDirectoryPath.toString();
        dataDirectoryPathString = dataDirectoryPath.toString();
    }

    public MasterDataDirectoryPaths(String masterDirectoryPathString, String dataDirectoryPathString) {
        this(Paths.get(masterDirectoryPathString), Paths.get(dataDirectoryPathString));
    }

    public Path pathInDirectory(Path directory, Path path) {
        return Paths.get(directory.toString(), path.toString());
    }

    public Path pathInMasterDirectory(Path path) {
        return pathInDirectory(masterDirectoryPath, path);
    }

    public Path pathInMasterDirectory(String path) {
        return pathInMasterDirectory(Paths.get(path));
    }

    public Path pathInDataDirectory(Path path) {
        return pathInDirectory(dataDirectoryPath, path);
    }

    public Path pathInDataDirectory(String path) {
        return pathInDataDirectory(Paths.get(path));
    }



}
