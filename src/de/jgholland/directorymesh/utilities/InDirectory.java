package de.jgholland.directorymesh.utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by john on 2015-10-28.
 */
public class InDirectory {
    Path directory;
    public InDirectory(Path directory) {
        this.directory = directory;
    }

    private Path getAbsolutePathForLocationWithinDirectory(String relativePath) {
        return Paths.get(directory.toString(), relativePath);
    }

    public void touchFile(String relativePath) throws Exception {
        FileCreationUtility.touchFile(getAbsolutePathForLocationWithinDirectory(relativePath));
    }

    public void createDirectory(String relativePath) throws Exception {
        FileCreationUtility.createDirectory(getAbsolutePathForLocationWithinDirectory(relativePath));
    }

    public void createSymlink(String relativeRealPath, String relativeSymlinkLocation) throws Exception {
        FileCreationUtility.createSymlink(
                getAbsolutePathForLocationWithinDirectory(relativeRealPath),
                getAbsolutePathForLocationWithinDirectory(relativeSymlinkLocation)
        );
    }

}
