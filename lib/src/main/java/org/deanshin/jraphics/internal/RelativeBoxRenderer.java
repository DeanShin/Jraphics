package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import javax.annotation.Nullable;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

class RelativeBoxRenderer implements Renderer<RelativeBox> {
	private final RenderingUtilService renderingUtilService;

	RelativeBoxRenderer(RenderingUtilService renderingUtilService) {
		this.renderingUtilService = renderingUtilService;
	}

	@Override
	public Class<RelativeBox> getElementClass() {
		return RelativeBox.class;
	}

	@Override
	public FinalizedBox getBounds(RelativeBox element, Graphics2D graphics2D, FinalizedBox parentBox, @Nullable FinalizedBox previousSiblingBox, ElementFlow flow) {
		Size.Pixel x = getX(parentBox, previousSiblingBox, element, flow);
		Size.Pixel y = getY(parentBox, previousSiblingBox, element, flow);

		return new FinalizedBox(
			x,
			y,
			renderingUtilService.sizeInPixels(element.getContent().getDimensions().getWidth(), parentBox.width()),
			renderingUtilService.sizeInPixels(element.getContent().getDimensions().getHeight(), parentBox.height()),
			element
		);
	}

	private Size.Pixel getX(FinalizedBox parentBox, FinalizedBox previousSiblingBox, RelativeBox relativeBox, ElementFlow flow) {
		ArrayList<Size.Pixel> candidates = new ArrayList<>();
		candidates.add(parentBox.x().plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getLeft(), parentBox.width())));
		if (parentBox.element() instanceof HasPadding hasPadding) {
			candidates.add(renderingUtilService.sizeInPixels(hasPadding.getPadding().getLeft(), parentBox.width()));
		}

		if (flow == ElementFlow.HORIZONTAL && previousSiblingBox != null) {
			candidates.add(previousSiblingBox.x().plus(previousSiblingBox.width())
				.plus(previousSiblingBox.element() instanceof HasMargin hasMargin ?
					renderingUtilService.sizeInPixels(hasMargin.getMargin().getRight(), parentBox.width()) :
					Size.ZERO
				));
			candidates.add(previousSiblingBox.x().plus(previousSiblingBox.width())
				.plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getLeft(), parentBox.width())));
		}

		return candidates.stream()
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
	}

	private Size.Pixel getY(FinalizedBox parentBox, FinalizedBox previousSiblingBox, RelativeBox relativeBox, ElementFlow flow) {
		ArrayList<Size.Pixel> candidates = new ArrayList<>();
		candidates.add(parentBox.y().plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height())));
		if (parentBox.element() instanceof HasPadding hasPadding) {
			candidates.add(renderingUtilService.sizeInPixels(hasPadding.getPadding().getTop(), parentBox.height()));
		}

		if (flow == ElementFlow.VERTICAL && previousSiblingBox != null) {
			candidates.add(previousSiblingBox.y().plus(previousSiblingBox.height())
				.plus(previousSiblingBox.element() instanceof HasMargin hasMargin ?
					renderingUtilService.sizeInPixels(hasMargin.getMargin().getBottom(), parentBox.height()) :
					Size.ZERO
				));
			candidates.add(previousSiblingBox.y().plus(previousSiblingBox.height())
				.plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height())));
		}

		return candidates.stream()
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
	}

	@Override
	public void render(RelativeBox element, Graphics2D graphics2D, FinalizedBox finalizedBox, FinalizedBox parentBox) {
		renderingUtilService.renderBox(graphics2D, element, finalizedBox);
		renderingUtilService.renderBorder(graphics2D, element, finalizedBox, parentBox);
	}
}
