package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.*;
import de.jgholland.directorymesh.utilities.FilePair;
import de.jgholland.directorymesh.utilities.MasterDataDirectoryPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 2015-10-26.
 */
public class ComparisonRunner {
    MasterDataDirectoryPaths masterDataDirectoryPaths;
    Pattern filesToIgnore = Pattern.compile("\\.DS_Store|\\.localized");

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

        if (fileMatchesIgnorePatterns(relativePathWithinDirectory)) {
            return new NullOperation(filePair);
        }

        if (filePair.neitherFileExists()) {
            return new ReportDataFileMissing(filePair);
        }

        if (filePair.masterExistsButDataDoesnt()) {
            if (filePair.masterIsABackLinkToAMissingFile()) {
                return new RemoveExistingLink(filePair);
            }
            return new NullOperation(filePair);
        }

        if (filePair.dataExistsButMasterDoesnt()) {
            if (filePair.dataIsDirectoryOrLinkToDirectory()) {
                return new MakeLinkForDirectory(filePair);
            } else {
                return new MakeLink(filePair);
            }
        }

        if (filePair.bothAreDirectories()) {
            return new NullOperation(filePair);
        }

        if (filePair.oneIsDirectoryAndOneIsRegular()) {
            return new ReportConflict("One file is a directory and the other is not", filePair);
        }

        if (filePair.masterIsABackLinkToTheCorrectDataFile()) {
            return new NullOperation(filePair);
        }

        if (filePair.bothExistAndMasterIsNotABackLink()) {
            return new ReportConflict("Both master and data exist, but master isn't a link back to data", filePair);
        }

        return new ReportConflict("Something strange with these files", filePair);
    }

    private boolean fileMatchesIgnorePatterns(Path relativePathWithinDirectory) {
        Path fileName = relativePathWithinDirectory.getFileName();
        String fileNameString = fileName.toString();
        Matcher matcher = filesToIgnore.matcher(fileNameString);
        boolean itMatches = matcher.matches();
        return itMatches;
    }

}
