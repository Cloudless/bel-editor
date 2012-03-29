package org.openbel.workbench.ui.interpreter;

import static org.openbel.workbench.core.Nature.BEL_NATURE;

import org.eclipse.dltk.internal.launching.StandardInterpreterRunner;
import org.eclipse.dltk.launching.AbstractInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.launching.IInterpreterRunner;

public class BELScriptInstall extends AbstractInterpreterInstall {
    public BELScriptInstall(IInterpreterInstallType type, String id) {
        super(type, id);
    }

    @Override
    public IInterpreterRunner getInterpreterRunner(String mode) {
        return new StandardInterpreterRunner(this);
    }

    @Override
    public String getNatureId() {
        return BEL_NATURE;
    }

}