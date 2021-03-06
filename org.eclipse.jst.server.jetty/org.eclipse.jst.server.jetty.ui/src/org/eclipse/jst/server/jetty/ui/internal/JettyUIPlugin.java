/*******************************************************************************
 * Copyright (c) 2010 Angelo Zerr and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo Zerr <angelo.zerr@gmail.com> - Initial API and implementation 
 *******************************************************************************/
package org.eclipse.jst.server.jetty.ui.internal;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JettyUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jst.server.jetty.ui"; //$NON-NLS-1$

	// base url for icons
	private static URL ICON_BASE_URL;
	private static final String URL_OBJ = "obj16/";
	private static final String URL_WIZBAN = "wizban/";
	public static final String IMG_WIZ_JETTY = "wizJetty";
	
	public static final String IMG_WEB_MODULE = "webModule";
	public static final String IMG_PORT = "port";
	public static final String IMG_PROJECT_MISSING = "projectMissing";

	// The shared instance
	private static JettyUIPlugin plugin;
	
	protected Map<String, ImageDescriptor> imageDescriptors = new HashMap<String, ImageDescriptor>();

	/**
	 * The constructor
	 */
	public JettyUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static JettyUIPlugin getDefault() {
		return plugin;
	}

	@Override
	protected ImageRegistry createImageRegistry() {
		ImageRegistry registry = new ImageRegistry();
		
		registerImage(registry, IMG_WIZ_JETTY, URL_WIZBAN + "jetty_wiz.png");
		
		registerImage(registry, IMG_WEB_MODULE, URL_OBJ + "web_module.gif");
		registerImage(registry, IMG_PORT, URL_OBJ + "port.gif");
//		registerImage(registry, IMG_WEB_MODULE, URL_OBJ + "web_module.gif");
//		registerImage(registry, IMG_MIME_MAPPING, URL_OBJ + "mime_mapping.gif");
//		registerImage(registry, IMG_MIME_EXTENSION, URL_OBJ + "mime_extension.gif");
//		registerImage(registry, IMG_PORT, URL_OBJ + "port.gif");
		registerImage(registry, IMG_PROJECT_MISSING, URL_OBJ + "project_missing.gif");
	
		return registry;
	}
	
	/**
	 * Return the image with the given key from the image registry.
	 * @param key java.lang.String
	 * @return org.eclipse.jface.parts.IImage
	 */
	public static Image getImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}
	
	/**
	 * Return the image with the given key from the image registry.
	 * @param key java.lang.String
	 * @return org.eclipse.jface.parts.IImage
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		try {
			getDefault().getImageRegistry();
			return (ImageDescriptor) getDefault().imageDescriptors.get(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Register an image with the registry.
	 * @param key java.lang.String
	 * @param partialURL java.lang.String
	 */
	private void registerImage(ImageRegistry registry, String key, String partialURL) {
		if (ICON_BASE_URL == null) {
			String pathSuffix = "icons/";
			ICON_BASE_URL = getDefault().getBundle().getEntry(pathSuffix);
		}

		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(new URL(ICON_BASE_URL, partialURL));
			registry.put(key, id);
			imageDescriptors.put(key, id);
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Error registering image", e);
		}
	}
}
