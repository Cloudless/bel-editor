/**
 * Copyright (c) 2012 Selventa.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html

 * Contributors:
 *     Selventa - initial API and implementation
 */

package org.openbel.editor.ui;

import static org.openbel.editor.ui.Activator.*;
import static org.openbel.editor.ui.UIFunctions.isBELScriptDocument;
import static org.openbel.editor.ui.UIFunctions.isBuilder;
import static org.openbel.editor.ui.UIFunctions.isXBELDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Renders labels for elements of the BEL Editor.
 */
public class OpenBELLabelProvider implements ILabelProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(final ILabelProviderListener l) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLabelProperty(final Object o, final String s) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(final ILabelProviderListener l) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(final Object o) {
        if (o instanceof IProject) {
            return getProjectIcon();
        } else if (o instanceof IFile) {
            IFile file = (IFile) o;
            if (isBELScriptDocument(file)) {
                return getBELScriptIcon();
            } else if (isXBELDocument(file)) {
                return getXBELIcon();
            } else if (isBuilder(file)) {
                return getBuilderIcon();
            }
        } else if (o instanceof IFolder) {
            return getDocumentGroupIcon();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final Object o) {
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof IProject) {
            return ((IProject) o).getName();
        } else if (o instanceof IFolder) {
            return ((IFolder) o).getName();
        } else if (o instanceof IFile) {
            return ((IFile) o).getName();
        }
        return null;
    }

}
