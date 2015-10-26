package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;

/**
 * Created by john on 2015-10-26.
 */
public class ComparisonRunner {

    String masterPath;
    String dataPath;

    public ComparisonRunner(String masterPath, String dataPath) {
        this.masterPath = masterPath;
        this.dataPath = dataPath;
    }

    public FileOperation pickOperation(String relativePathWithinDataDirectory) {
        return null;
    }
}
