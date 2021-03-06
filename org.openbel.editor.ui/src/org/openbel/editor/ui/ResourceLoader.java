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

import static org.openbel.editor.core.common.BELUtilities.createDirectory;
import static org.openbel.editor.core.common.BELUtilities.readable;
import static org.openbel.editor.core.common.PathConstants.SYSCONFIG_FILENAME;
import static org.openbel.editor.ui.Activator.getDefault;
import static org.openbel.editor.ui.Activator.getPluginLocation;
import static org.openbel.editor.ui.UIFunctions.logError;
import static org.openbel.editor.ui.util.StackUtilities.callerFrame;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.openbel.editor.core.index.Parser;
import org.openbel.editor.core.index.ResourceIndex;
import org.openbel.editor.core.record.RecordFile;
import org.openbel.editor.core.record.Records;
import org.openbel.editor.ui.views.AnnotationView;
import org.openbel.editor.ui.views.NamespaceView;
import org.openbel.editor.ui.views.ResourceView;

/**
 * {@link ResourceLoader} loads namespace and annotation resources from the
 * resource index configured in the BEL Framework Home location pointed to by
 * {@link Activator#getBELFrameworkHome()}.
 */
final class ResourceLoader {
    private final ISchedulingRule rule = new ResourceRule();

    /**
     * Run the load resource {@link ResourceJob job}. This job:
     * <ol>
     * <li>Reads the resource index from the BEL Framework's system
     * configuration</li>
     * <li>Updates the {@link ResourceView resource view},
     * {@link AnnotationView annotation view}, and {@link NamespaceView
     * namespace view} with the available resources from the resource index.</li>
     * <li>Builds the {@link RecordFile record file} for each annotation or
     * namespace resource, if necessary.</li>
     * </ol>
     */
    public void loadResourceIndex() {
        final ResourceJob resJob = new ResourceJob();
        resJob.setRule(rule);
        resJob.schedule();
    }

    /**
     * {@link ResourceJob} defines an eclipse {@link Job job} that loads the BEL
     * Framework resource index in the background.
     */
    private final class ResourceJob extends Job {

        /**
         * Construct the {@link Job job} providing the job name.
         */
        public ResourceJob() {
            super("Loading Resources");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected IStatus run(IProgressMonitor m) {
            m.beginTask("Loading Resources", 100);

            // read resource index
            m.subTask("Loading resource index.");
            final ResourceIndex resourceIndex = readResourceIndex();
            if (resourceIndex == null) {
                m.setCanceled(true);
                return Status.CANCEL_STATUS;
            }

            // advance progress bar to 50%
            m.worked(50);

            // set resource index in UI plugin and update views with new
            // resources
            getDefault().setResourceIndex(resourceIndex);
            Display.getDefault().asyncExec(new UpdateViewResources());

            m.subTask("Building namespace and resources.");

            // create UI plugin 'resources/' directory if necessary
            final ResourceIndex index = getDefault().getResourceIndex();
            if (index == null) {
                logError("resource index is null at " + callerFrame());
                m.setCanceled(true);
                return Status.CANCEL_STATUS;
            }

            final String rloc = getPluginLocation().append("resources")
                    .makeAbsolute().toString();
            createDirectory(rloc);
            final File rlocFile = new File(rloc);

            try {
                // build the record files into the 'resources/' directory
                final Records records = new Records(rlocFile, resourceIndex);
                records.build(index);
            } catch (IOException e) {
                logError(e);
            }

            return Status.OK_STATUS;
        }

        /**
         * Reads the {@link ResourceIndex resource index} from the BEL Framework
         * Home configured in {@link Activator#getBELFrameworkHome()}.
         *
         * @return ResourceIndex the resource index or {@code null} if an error
         *         occurred.
         */
        private ResourceIndex readResourceIndex() {
            final String belFrameworkHome = getDefault().getBELFrameworkHome();
            final StringBuilder b = new StringBuilder(belFrameworkHome)
            .append(File.separator).append("config")
            .append(File.separator).append(SYSCONFIG_FILENAME);

            final File configFile = new File(b.toString());
            if (!readable(configFile)) {
                logError(configFile + " not readable");
                return null;
            }

            try {
                final Properties scprops = new Properties();
                scprops.load(new FileReader(configFile));

                final String rurl = scprops.getProperty("resource_index_url");
                final URL url = new URL(rurl);

                ResourceIndex resourceIndex = Parser.getInstance().parse(url);
                return resourceIndex;
            } catch (Exception e) {
                e.printStackTrace();
                logError(e);
                return null;
            }
        }
    }

    /**
     * {@link UpdateViewResources} is a {@link Runnable} task to update the
     * views with the available resources.
     */
    private class UpdateViewResources implements Runnable {

        /**
         * Reloads the available resources in the Resources, Namespaces, and
         * Annotations view.
         *
         * {@inheritDoc}
         */
        @Override
        public void run() {
            try {
                final IWorkbenchPage page = PlatformUI
                        .getWorkbench()
                        .getActiveWorkbenchWindow()
                        .getActivePage();
                final IViewPart rpart = page
                        .showView(ResourceView.ID);
                if ((rpart != null)
                        && (rpart instanceof ResourceView)) {
                    final ResourceView rview = (ResourceView) rpart;
                    rview.loadContent();
                }

                final IViewPart npart = page
                        .findView(NamespaceView.ID);
                if ((npart != null)
                        && (npart instanceof NamespaceView)) {
                    final NamespaceView nview = (NamespaceView) npart;
                    nview.loadContent();
                }

                final IViewPart apart = page
                        .findView(AnnotationView.ID);
                if ((apart != null)
                        && (apart instanceof AnnotationView)) {
                    final AnnotationView aview = (AnnotationView) apart;
                    aview.loadContent();
                }
            } catch (PartInitException e) {
                logError(e);
            }
        }
    }

    /**
     * {@link ResourceRule} defines a {@link ISchedulingRule rule} that is used
     * to synchronously run the ResourceIndex job then the Resource job.
     */
    private class ResourceRule implements ISchedulingRule {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }
    }
}
