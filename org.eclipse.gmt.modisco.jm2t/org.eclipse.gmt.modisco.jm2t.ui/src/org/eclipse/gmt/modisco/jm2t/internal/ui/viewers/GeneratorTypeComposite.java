/*******************************************************************************
 * Copyright (c) 2010 Angelo ZERR.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:      
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.gmt.modisco.jm2t.internal.ui.viewers;

import org.eclipse.gmt.modisco.jm2t.core.generator.IGeneratorType;
import org.eclipse.gmt.modisco.jm2t.internal.ui.Messages;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 * Display a tree which display generator type available.
 * 
 */
public class GeneratorTypeComposite extends AbstractTreeComposite {

	protected IGeneratorType selection;
	protected GeneratorTypeSelectionListener listener;
	protected boolean creation;
	protected String type;
	protected String version;

	protected GeneratorTypeTreeContentProvider contentProvider;
	protected boolean initialSelection = true;

	public interface GeneratorTypeSelectionListener {
		public void generatorTypeSelected(IGeneratorType generatorType);
	}

	public GeneratorTypeComposite(final Composite parent,
			final GeneratorTypeSelectionListener listener) {
		super(parent);
		this.listener = listener;

		contentProvider = new GeneratorTypeTreeContentProvider();
		treeViewer.setContentProvider(contentProvider);

		ILabelProvider labelProvider = new GeneratorTypeTreeLabelProvider();
		labelProvider.addListener(new ILabelProviderListener() {
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				Object[] obj = event.getElements();
				if (obj == null)
					treeViewer.refresh(true);
				else {
					int size = obj.length;
					for (int i = 0; i < size; i++)
						treeViewer.refresh(obj[i], true);
				}
			}
		});
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setInput(AbstractTreeContentProvider.ROOT);

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Object obj = getSelection(event.getSelection());
				if (obj instanceof IGeneratorType) {
					selection = (IGeneratorType) obj;
					setDescription(selection.getDescription());
				} else {
					selection = null;
					setDescription("");
				}
				listener.generatorTypeSelected(selection);
			}
		});

		treeViewer.expandAll();
		// treeViewer.setSorter(new DefaultViewerSorter());
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && initialSelection) {
			initialSelection = false;
			if (contentProvider.getInitialSelection() != null)
				treeViewer.setSelection(
						new StructuredSelection(contentProvider
								.getInitialSelection()), true);
		}
	}

	protected String getTitleLabel() {
		return Messages.GeneratorTypeComposite_title;
	}

	public IGeneratorType getSelectedGeneratorType() {
		return selection;
	}

	@Override
	protected String getDescriptionLabel() {
		return null;
	}
}
