package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-10-26.
 */
public class MakeLinkForDirectory extends MakeLink {
    public MakeLinkForDirectory(FilePair filePair) {
        super(filePair);
        stopFileVisitorFromVisitingSubdirectories();
    }

    @Override
    public void runOperation() {

    }

}
