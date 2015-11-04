package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-29.
 */

public class ReportDataFileMissing extends NullOperation {

    public ReportDataFileMissing(FilePair filePair) {
        super(filePair);
    }

    @Override
    public void reportOperation() {
        System.err.printf("%s is missing.%n", filePair.abbreviatedDataPath());
    }

}
