package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Text;

import javax.annotation.Nullable;
import java.awt.Graphics2D;

class TextRenderer implements Renderer<Text> {
	public Class<Text> getElementClass() {
		return Text.class;
	}

	@Override
	public FinalizedBox getBounds(Text element, Graphics2D graphics2D, FinalizedBox parentBox, @Nullable FinalizedBox previousSiblingBox) {
		return parentBox;
	}

	@Override
	public void render(Text element, Graphics2D graphics2D, FinalizedBox finalizedBox, FinalizedBox parentBox) {

	}
}
