package de.jgholland.directorymesh.operations;

import java.nio.file.FileVisitResult;

/**
 * Created by john on 2015-10-26.
 */
public interface FileOperation {

    void reportOperation();

    void runOperation();

    FileVisitResult getFileVisitResult();
}
