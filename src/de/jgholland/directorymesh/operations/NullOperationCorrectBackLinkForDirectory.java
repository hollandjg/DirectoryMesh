package de.jgholland.directorymesh.operations;

import de.jgholland.directorymesh.utilities.FilePair;

/**
 * Created by john on 2015-11-04.
 */
public class NullOperationCorrectBackLinkForDirectory extends NullOperationCorrectBackLink {
        public NullOperationCorrectBackLinkForDirectory(FilePair filePair) {
            super(filePair);
            stopFileVisitorFromVisitingSubdirectories();
        }

}
