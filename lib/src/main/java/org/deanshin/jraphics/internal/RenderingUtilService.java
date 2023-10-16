package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Element;
import org.deanshin.jraphics.datamodel.HasBorder;
import org.deanshin.jraphics.datamodel.HasBox;
import org.deanshin.jraphics.datamodel.Size;

import java.awt.Graphics2D;

interface RenderingUtilService {
	Size.Pixel sizeInPixels(Size size, Size.Pixel parentSize);

	FinalizedBox contentBox(FinalizedBox finalizedBox, FinalizedBox parentBox, Element element);

	void renderBox(Graphics2D graphics, HasBox hasBox, FinalizedBox finalizedBox);

	void renderBorder(Graphics2D graphics, HasBorder hasBorder, FinalizedBox finalizedBox, FinalizedBox parentBox);
}
