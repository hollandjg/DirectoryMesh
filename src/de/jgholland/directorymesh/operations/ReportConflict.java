package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */
public class ReportConflict extends NullOperation {
    String conflictMessage;

    public ReportConflict(String message, FilePair filePair) {
        super(filePair);
        this.conflictMessage = message;
    }

    @Override
    public void reportOperation() {
        System.err.printf("Conflict %s: %s%n", filePair.getRelativePathWithinDirectories(), conflictMessage);
    }

}
