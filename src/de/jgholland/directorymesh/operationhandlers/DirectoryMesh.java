package de.jgholland.directorymesh.operationhandlers;

import de.jgholland.directorymesh.operations.FileOperation;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by john on 2015-10-26.
 */
public class DirectoryMesh {
    final ComparisonRunner comparisonRunner;
    final OperationRunner operationRunner;
    Path masterPath;
    Path dataPath;

    public DirectoryMesh(String masterPath, String dataPath, boolean quiet, boolean dryRun) {
        this.comparisonRunner = new ComparisonRunner(masterPath, dataPath);
        this.operationRunner = new OperationRunner(quiet, dryRun);
        this.masterPath = Paths.get(masterPath);
        this.dataPath = Paths.get(dataPath);
    }

    public void linkDataFilesIntoMaster() {
        fileTreeWalkerErrorWrapper(dataPath);
    }

    public void pruneMasterDirectoryBackLinks() {
        fileTreeWalkerErrorWrapper(masterPath);
    }


    private void fileTreeWalkerErrorWrapper(Path path) {
            try {
                walkTheFileTreeCarryingOutOperationsAtEachRelevantFile(path);
            } catch (IOException e) {
                System.out.printf("Directory walk failed: %s%n", e);
            }
        }


    void walkTheFileTreeCarryingOutOperationsAtEachRelevantFile(Path pathToWalk) throws IOException {

        Files.walkFileTree(
                pathToWalk,
                new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path filePath, BasicFileAttributes attrs) {
                        return getFileVisitResult(filePath);
                    }

                    @Override
                    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
                        return getFileVisitResult(filePath);

                    }

                    private FileVisitResult getFileVisitResult(Path filePath) {
                        Path relativeDataPath = pathToWalk.relativize(filePath);
                        FileOperation individualOperation = comparisonRunner.pickOperation(relativeDataPath);
                        operationRunner.processFileOperation(individualOperation);
                        FileVisitResult fileVisitResult = individualOperation.getFileVisitResult();
                        return fileVisitResult;
                    }
                });
    }
}
