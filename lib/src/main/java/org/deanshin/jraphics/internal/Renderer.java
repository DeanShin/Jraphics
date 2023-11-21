package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Element;
import org.deanshin.jraphics.datamodel.ElementFlow;

import javax.annotation.Nullable;
import java.awt.Graphics2D;

interface Renderer<T extends Element> {
	Class<T> getElementClass();

	FinalizedBox getBounds(
		T element,
		Graphics2D graphics2D,
		FinalizedBox parentBox,
		@Nullable FinalizedBox previousSiblingBox,
		ElementFlow flow
	);

	void render(T element, Graphics2D graphics2D, FinalizedBox finalizedBox, FinalizedBox parentBox);
}
