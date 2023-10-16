package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.AbsoluteBox;

import javax.annotation.Nullable;
import java.awt.Graphics2D;

class AbsoluteBoxRenderer implements Renderer<AbsoluteBox> {
	private final RenderingUtilService renderingUtilService;

	AbsoluteBoxRenderer(RenderingUtilService renderingUtilService) {
		this.renderingUtilService = renderingUtilService;
	}

	@Override
	public Class<AbsoluteBox> getElementClass() {
		return AbsoluteBox.class;
	}

	@Override
	public FinalizedBox getBounds(AbsoluteBox element, Graphics2D graphics2D, FinalizedBox parentBox, @Nullable FinalizedBox previousSiblingBox) {
		return new FinalizedBox(
			parentBox.x().plus(renderingUtilService.sizeInPixels(element.getX(), parentBox.width())),
			parentBox.y().plus(renderingUtilService.sizeInPixels(element.getY(), parentBox.height())),
			renderingUtilService.sizeInPixels(element.getBox().getDimensions().getWidth(), parentBox.width()),
			renderingUtilService.sizeInPixels(element.getBox().getDimensions().getHeight(), parentBox.height()),
			element
		);
	}

	@Override
	public void render(AbsoluteBox element, Graphics2D graphics2D, FinalizedBox finalizedBox, FinalizedBox parentBox) {
		renderingUtilService.renderBox(graphics2D, element, finalizedBox);
		renderingUtilService.renderBorder(graphics2D, element, finalizedBox, parentBox);
	}
}
