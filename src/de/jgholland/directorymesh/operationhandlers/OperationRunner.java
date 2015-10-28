package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;

/**
 * Created by john on 2015-10-26.
 */
public class OperationRunner {
    boolean quiet;
    boolean dryRun;

    public OperationRunner(boolean quiet, boolean dryRun) {
        this.quiet = quiet;
        this.dryRun = dryRun;
    }

    public void processFileOperation(FileOperation operation) {
        if (!quiet) {
            operation.reportOperation();
        }
        if (!dryRun) {
            operation.runOperation();
        }
    }
}
