package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Window;

import java.awt.Graphics2D;

public interface WindowRenderer {
	void render(Window window, Graphics2D graphics);
}
