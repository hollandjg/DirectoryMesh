package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Created by john on 2015-10-26.
 */
public class DirectoryMesh {
    private final ComparisonRunner comparisonRunner;
    private final OperationRunner operationRunner;
    Path masterPath;
    Path dataPath;
    private ArrayList<FileOperation> allTheOperationsToPerform = new ArrayList<>();

    public DirectoryMesh(String masterPath, String dataPath, boolean quiet, boolean dryRun) {
        this.comparisonRunner = new ComparisonRunner(masterPath, dataPath);
        this.operationRunner = new OperationRunner(quiet, dryRun);
        this.masterPath = Paths.get(masterPath);
        this.dataPath = Paths.get(dataPath);
    }

    public void runDirectoryMesh() throws Exception {
        buildListOfOperations();
        runOperations();
    }

    private void runOperations() {
        for (FileOperation individualOperation : allTheOperationsToPerform) {
            operationRunner.processFileOperation(individualOperation);
        }
    }


    private void buildListOfOperations() throws Exception {
        Files.walkFileTree(
                dataPath,
                new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path filePath, BasicFileAttributes attrs) throws IOException {
                        return getFileVisitResult(filePath);
                    }

                    @Override
                    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                        return getFileVisitResult(filePath);

                    }

                    private FileVisitResult getFileVisitResult(Path filePath) throws IOException {
                        FileOperation individualOperation;
                        Path relativeDataPath = dataPath.relativize(filePath);

                        System.out.printf("Testing %s%n", relativeDataPath);

                        try {
                            individualOperation = comparisonRunner.pickOperation(relativeDataPath);
                        } catch (Exception e) {
                            throw new IOException(e.getMessage());
                        }
                        allTheOperationsToPerform.add(individualOperation);
                        return individualOperation.getFileVisitResult();
                    }
                });

    }
    

}
