/**
 * Copyright (c) 2012 Selventa.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html

 * Contributors:
 *     Selventa - initial API and implementation
 */

package org.openbel.editor.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.openbel.editor.ui.views.AnnotationView;
import org.openbel.editor.ui.views.NamespaceView;
import org.openbel.editor.ui.views.ResourceView;

public class BELEditorPerspective implements IPerspectiveFactory {
    public static final String ID = "org.openbel.editor.perspective";
    private IPageLayout factory;

    private void addActionSets() {
        factory.addActionSet("org.eclipse.debug.ui.launchActionSet"); // NON-NLS-1
        factory.addActionSet("org.eclipse.debug.ui.debugActionSet"); // NON-NLS-1
        factory.addActionSet("org.eclipse.debug.ui.profileActionSet"); // NON-NLS-1
        factory.addActionSet("org.eclipse.team.ui.actionSet"); // NON-NLS-1
        factory.addActionSet("org.eclipse.ant.ui.actionSet.presentation"); // NON-NLS-1
        factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

        // factory.addActionSet(JavaUI.ID_ACTION_SET);
        // factory.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
    }

    private void addNewWizardShortcuts() {
        factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder"); // NON-NLS-1
        factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.file"); // NON-NLS-1
        factory.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard"); //$NON-NLS-1$
        factory.addNewWizardShortcut("com.googlecode.goclipse.wizards.project.mainwiz"); // NON-NLS-1
        factory.addNewWizardShortcut("com.googlecode.goclipse.wizards.NewGoFileWizard"); // NON-NLS-1
    }

    private void addPerspectiveShortcuts() {
        factory.addPerspectiveShortcut("org.eclipse.team.ui.TeamSynchronizingPerspective"); // NON-NLS-1
        factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective"); // NON-NLS-1
    }

    public void addViews() {
        IFolderLayout topLeft = factory.createFolder("topLeft", // NON-NLS-1
                IPageLayout.LEFT, 0.15f, factory.getEditorArea());

        topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer"); // NON-NLS-1
        IFolderLayout bottomLeft = factory.createFolder("bottomLeft",
                IPageLayout.BOTTOM, 0.5f,
                "org.eclipse.ui.navigator.ProjectExplorer");

        bottomLeft.addView(NamespaceView.ID);
        bottomLeft.addView(AnnotationView.ID);

        IFolderLayout topRight = factory.createFolder("topRight",
                IPageLayout.RIGHT, 0.80f, factory.getEditorArea());
        topRight.addView(ResourceView.ID);

        IFolderLayout bottom = factory.createFolder("bottomRight", // NON-NLS-1
                IPageLayout.BOTTOM, 0.75f, factory.getEditorArea());

        bottom.addView(IPageLayout.ID_PROGRESS_VIEW);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
        bottom.addView("org.eclipse.team.ui.GenericHistoryView"); // NON-NLS-1
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
        bottom.addPlaceholder("org.eclipse.search.ui.views.SearchView");
        bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
        bottom.addPlaceholder(IPageLayout.ID_TASK_LIST);

    }

    @SuppressWarnings("deprecation")
    private void addViewShortcuts() {
        factory.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView"); // NON-NLS-1
        factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
        factory.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        factory.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        factory.addShowViewShortcut("org.eclipse.search.ui.views.SearchView");
        factory.addShowViewShortcut("org.eclipse.pde.runtime.LogView"); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createInitialLayout(IPageLayout factory) {
        this.factory = factory;
        addViews();
        addActionSets();
        addPerspectiveShortcuts();
        addNewWizardShortcuts();
        addViewShortcuts();
    }

}
