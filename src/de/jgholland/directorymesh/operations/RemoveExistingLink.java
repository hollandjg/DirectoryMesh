package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */

public class RemoveExistingLink extends OperationOnFilePair {

    public RemoveExistingLink(FilePair filePair) {
        super(filePair);


    }

    @Override
    public void reportOperation() {
        System.out.printf("Removing link %s%n", filePair.masterPath);
    }

    @Override
    public void runOperation() {

    }
}
