package de.jgholland.directorymesh.operationhandlers;

/**
 * Created by john on 2015-10-26.
 */
public class DirectoryMesh {
    private final ComparisonRunner comparisonRunner;
    private final OperationRunner operationRunner;

    public DirectoryMesh(String masterPath, String dataPath, boolean quiet, boolean dryRun) {
        this.comparisonRunner = new ComparisonRunner(masterPath, dataPath);
        this.operationRunner = new OperationRunner(quiet, dryRun);
    }

    public void runDirectoryMesh() {
    }

}
