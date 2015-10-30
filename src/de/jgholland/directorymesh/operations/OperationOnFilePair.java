package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;

/**
 * Created by john on 2015-10-29.
 */
public abstract class OperationOnFilePair implements FileOperation {

    Path masterPath;
    Path dataPath;
    FilePair filePair;
    String message;
    FileVisitResult fileVisitResult;

    public OperationOnFilePair(String message, FilePair filePair, FileVisitResult fileVisitResult) {
        this.filePair = filePair;
        this.masterPath = filePair.masterPath;
        this.dataPath = filePair.dataPath;
        this.message = message;
        this.fileVisitResult = fileVisitResult;
    }

    @Override
    public void reportOperation() {
        System.out.printf("%s: %s%n", message, filePair.getRelativePathWithinDirectories());
    }

    @Override
    public FileVisitResult getFileVisitResult() {
        return fileVisitResult;
    }

}
