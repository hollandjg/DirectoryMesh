package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */
public class NullOperationCorrectBackLink extends NullOperation {

    public NullOperationCorrectBackLink(FilePair filePair) {
        super(filePair);
    }

    @Override
    public void reportOperation() {
        System.out.printf("(Correct backlink: %s)%n", filePair.getRelativePathWithinDirectories()); // Do nothing.
    }
}
