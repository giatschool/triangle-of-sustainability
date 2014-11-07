package de.ifgi.worldwind.htc;

import java.awt.event.*;

/**
 * The famous WindowClosingAdapter
 * 
 * @author flowdie
 * 
 */
public class WindowClosingAdapter extends WindowAdapter {
	private boolean exitSystem;

	public WindowClosingAdapter(boolean exitSystem) {
		this.exitSystem = exitSystem;
	}

	public WindowClosingAdapter() {
		this(true);
	}

	public void windowClosing(WindowEvent event) {
		event.getWindow().setVisible(false);
		event.getWindow().dispose();
		if (exitSystem) {
			System.exit(0);
		}
	}
}