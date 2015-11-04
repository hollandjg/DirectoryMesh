package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-11-04.
 */
public class ReportConflictForDirectory extends ReportConflict {
    public ReportConflictForDirectory(String message, FilePair filePair) {
        super(message, filePair);
        stopFileVisitorFromVisitingSubdirectories();
    }
}
