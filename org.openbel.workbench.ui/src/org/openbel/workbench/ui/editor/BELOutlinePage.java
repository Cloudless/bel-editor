package org.openbel.workbench.ui.editor;

import static java.lang.System.out;
import static org.openbel.workbench.ui.util.StackUtilities.myFrame;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class BELOutlinePage implements IContentOutlinePage {

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControl(Composite parent) {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control getControl() {
        out.println(myFrame());
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActionBars(IActionBars bars) {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFocus() {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSelectionChangedListener(ISelectionChangedListener l) {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISelection getSelection() {
        out.println(myFrame());
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSelectionChangedListener(ISelectionChangedListener l) {
        out.println(myFrame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelection(ISelection sel) {
        out.println(myFrame());
    }

}