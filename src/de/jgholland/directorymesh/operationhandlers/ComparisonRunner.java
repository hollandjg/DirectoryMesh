package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;
import de.jgholland.directorymesh.operations.ReportConflict;
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

        if (filePair.bothFilesAreReal()) {
            return new ReportConflict("Both files are real (not symlinks)", filePair);
        }
        if (filePair.oneIsDirectoryAndOneIsRegular()) {
            return new ReportConflict("One file is a directory and the other is not", filePair);
        }

//        if (filePair.masterRegularAndLinkOnData()) {
//            return new ReportConflict("The master file is regular!", filePair);
//        }

        return null;


    }
}
