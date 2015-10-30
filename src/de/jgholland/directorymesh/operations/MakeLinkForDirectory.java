package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

import java.nio.file.FileVisitResult;

/**
 * Created by john on 2015-10-26.
 */
public class MakeLinkForDirectory extends OperationOnFilePair {
    public MakeLinkForDirectory(String message, FilePair filePair) {
        super(message, filePair, FileVisitResult.SKIP_SUBTREE);
    }

    @Override
    public void runOperation() {

    }
}
