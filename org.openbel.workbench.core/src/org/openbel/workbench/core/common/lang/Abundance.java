package org.openbel.workbench.core.common.lang;

import org.openbel.workbench.core.common.Strings;

/**
 * Denotes the abundance of an entity.
 * <p>
 * Function {@link Signature signature(s)}:
 * 
 * <pre>
 * abundance(E:abundance)abundance
 * </pre>
 * 
 * </p>
 * 
 * @see Signature
 */
public class Abundance extends Function {

    /**
     * {@link Strings#ABUNDANCE}
     */
    public final static String NAME;

    /**
     * {@link Strings#ABUNDANCE_ABBREV}
     */
    public final static String ABBREVIATION;

    /**
     * Function description.
     */
    public final static String DESC;

    static {
        NAME = Strings.ABUNDANCE;
        ABBREVIATION = Strings.ABUNDANCE_ABBREV;
        DESC = "Denotes the abundance of an entity";
    }

    public Abundance() {
        super(NAME, ABBREVIATION, DESC,
                "abundance(E:abundance)abundance");
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