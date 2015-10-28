package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

import java.nio.file.Path;

/**
 * Created by john on 2015-10-26.
 */
public class ReportConflict implements FileOperation {
    Path masterPath;
    Path dataPath;
    String message;

    public ReportConflict(String message, Path masterPath, Path dataPath) {
        this.masterPath = masterPath;
        this.dataPath = dataPath;
        this.message = message;
    }

    public ReportConflict(String message, FilePair filePair) {
        this(message, filePair.masterPath, filePair.dataPath);
    }

    @Override
    public void reportOperation() {
        System.out.printf("%s: [master] %s [data] %s%n", message, masterPath, dataPath);
    }

    @Override
    public void runOperation() {

    }
}
