/**
 * Copyright (c) 2012 Selventa.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html

 * Contributors:
 *     Selventa - initial API and implementation
 */

package org.openbel.editor.core.common.lang;

import org.openbel.editor.core.common.Strings;

/**
 * Denotes the frequency or abundance of events in which a member directly acts
 * to control transcription of genes.
 * <p>
 * Function {@link Signature signature(s)}:
 * 
 * <pre>
 * transcriptionalActivity(F:abundance)abundance
 * </pre>
 * 
 * </p>
 * 
 * @see Signature
 */
public class TranscriptionalActivity extends Function {

    /**
     * {@link Strings#TRANSCRIPTIONAL_ACTIVITY}
     */
    public final static String NAME;

    /**
     * {@link Strings#TRANSCRIPTIONAL_ACTIVITY_ABBREV}
     */
    public final static String ABBREVIATION;

    /**
     * Function description.
     */
    public final static String DESC;

    static {
        NAME = Strings.TRANSCRIPTIONAL_ACTIVITY;
        ABBREVIATION = Strings.TRANSCRIPTIONAL_ACTIVITY_ABBREV;
        DESC = "Denotes the frequency or abundance of events in which a " +
                "member directly acts to control transcription of genes";
    }

    public TranscriptionalActivity() {
        super(NAME, ABBREVIATION, DESC,
                "transcriptionalActivity(F:complexAbundance)abundance",
                "transcriptionalActivity(F:proteinAbundance)abundance");
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public boolean validArgumentCount(int count) {
        switch (count) {
        case 1:
            return true;
        default:
            return false;
        }
    }
}
