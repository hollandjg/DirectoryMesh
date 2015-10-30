package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

import java.nio.file.FileVisitResult;

/**
 * Created by john on 2015-10-26.
 */
public class NullOperation extends OperationOnFilePair {

    public NullOperation(String message, FilePair filePair) {
        super(message, filePair, FileVisitResult.CONTINUE);
    }

    @Override
    public final void runOperation() {
        // Do nothing.
    }
}
