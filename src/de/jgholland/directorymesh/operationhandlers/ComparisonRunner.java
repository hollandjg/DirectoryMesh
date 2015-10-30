package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.*;
import de.jgholland.directorymesh.utilities.FilePair;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by john on 2015-10-26.
 */
public class ComparisonRunner {
    MasterDataDirectoryPaths masterDataDirectoryPaths;

    public ComparisonRunner(String masterPath, String dataPath) {
        this(new MasterDataDirectoryPaths(masterPath, dataPath));
    }

    public ComparisonRunner(MasterDataDirectoryPaths masterDataDirectoryPaths) {
        this.masterDataDirectoryPaths = masterDataDirectoryPaths;
    }

    public FileOperation pickOperation(String relativePathWithinDirectory) throws Exception {
        return pickOperation(Paths.get(relativePathWithinDirectory));
    }

    public FileOperation pickOperation(Path relativePathWithinDirectory) throws Exception {

        FilePair filePair = new FilePair(relativePathWithinDirectory, masterDataDirectoryPaths);

        if (filePair.neitherFileExists()) {
            return new ReportDataFileMissing("Conflict: Something's gone wrong, neither file exists", filePair);
        }

        if (filePair.masterExistsButDataDoesnt()) {
            if (filePair.masterIsABackLinkToAMissingFile()) {
                return new RemoveExistingLink("Remove: The link back to the data file is broken -- remove it", filePair);
            }
            return new NullOperation("Null: File only exists on master: nothing to do.", filePair);
        }

        if (filePair.dataExistsButMasterDoesnt()) {
            if (filePair.dataIsDirectoryOrLinkToDirectory()) {
                return new MakeLinkForDirectory("Link: Linking these directories together", filePair);
            } else {
                return new MakeLink("Link: Linking these files together", filePair);
            }
        }

        if (filePair.bothAreDirectories()) {
            return new NullOperation("Null: Both are directories", filePair);
        }

        if (filePair.oneIsDirectoryAndOneIsRegular()) {
            return new ReportConflict("Conflict: One file is a directory and the other is not", filePair);
        }

        if (filePair.masterIsABackLinkToTheCorrectDataFile()) {
            return new NullOperation("Null: The backlink is correct", filePair);
        }

        if (filePair.bothExistAndMasterIsNotABackLink()) {
            return new ReportConflict("Conflict: Both master and data exist, but master isn't a link back to data", filePair);
        }

        return new ReportConflict("Conflict: Something strange with these files", filePair);
    }
    
}
