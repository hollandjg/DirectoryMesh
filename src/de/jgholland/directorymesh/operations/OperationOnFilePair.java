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
    FileVisitResult fileVisitResult;

    public OperationOnFilePair(FilePair filePair) {
        this.filePair = filePair;
        this.masterPath = filePair.masterPath;
        this.dataPath = filePair.dataPath;
        allowFileVisitorToVisitSubdirectories();
    }

    private void allowFileVisitorToVisitSubdirectories() {
        this.fileVisitResult = FileVisitResult.CONTINUE;
    }

    protected void stopFileVisitorFromVisitingSubdirectories() {
        this.fileVisitResult = FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult getFileVisitResult() {
        return this.fileVisitResult;
    }

}
