package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

import java.nio.file.Path;

/**
 * Created by john on 2015-10-29.
 */
public abstract class OperationOnFilePair implements FileOperation {

    Path masterPath;
    Path dataPath;
    FilePair filePair;
    String message;

    public OperationOnFilePair(String message, FilePair filePair) {
        this.filePair = filePair;
        this.masterPath = filePair.masterPath;
        this.dataPath =  filePair.dataPath;
        this.message = message;
    }

    @Override
    public void reportOperation() {
        System.out.printf("%s: %s", filePair.getRelativePathWithinDirectories(), message);
    }

}
