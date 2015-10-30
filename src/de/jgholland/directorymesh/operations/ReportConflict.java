package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */
public class ReportConflict extends NullOperation {

    public ReportConflict(String message, FilePair filePair) {
        super(message, filePair);
    }

}
