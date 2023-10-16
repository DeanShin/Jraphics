package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.HasMargin;
import org.deanshin.jraphics.datamodel.HasPadding;
import org.deanshin.jraphics.datamodel.RelativeBox;
import org.deanshin.jraphics.datamodel.Size;

import javax.annotation.Nullable;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.stream.Stream;

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
	public FinalizedBox getBounds(RelativeBox element, Graphics2D graphics2D, FinalizedBox parentBox, @Nullable FinalizedBox previousSiblingBox) {
		Size.Pixel x = getX(parentBox, element);
		Size.Pixel y = getY(parentBox, previousSiblingBox, element);

		return new FinalizedBox(
			x,
			y,
			renderingUtilService.sizeInPixels(element.getBox().getDimensions().getWidth(), parentBox.width()),
			renderingUtilService.sizeInPixels(element.getBox().getDimensions().getHeight(), parentBox.height()),
			element
		);
	}

	private Size.Pixel getX(FinalizedBox parentBox, RelativeBox relativeBox) {
		return Stream.of(
				Stream.of(parentBox.x().plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getLeft(), parentBox.width()))),
				parentBox.element() instanceof HasPadding hasPadding ?
					Stream.of(renderingUtilService.sizeInPixels(hasPadding.getPadding().getLeft(), parentBox.width())) :
					Stream.<Size.Pixel>empty()
			)
			.flatMap(s -> s)
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
	}

	private Size.Pixel getY(FinalizedBox parentBox, FinalizedBox previousSiblingBox, RelativeBox relativeBox) {
		return Stream.of(
				previousSiblingBox == null ?
					Stream.<Size.Pixel>empty() :
					Stream.of(
						previousSiblingBox.y().plus(previousSiblingBox.height())
							.plus(previousSiblingBox.element() instanceof HasMargin hasMargin ?
								renderingUtilService.sizeInPixels(hasMargin.getMargin().getBottom(), parentBox.height()) :
								Size.ZERO
							),
						previousSiblingBox.y().plus(previousSiblingBox.height())
							.plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height()))
					),
				Stream.of(parentBox.y().plus(renderingUtilService.sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height()))),
				parentBox.element() instanceof HasPadding hasPadding ?
					Stream.of(renderingUtilService.sizeInPixels(hasPadding.getPadding().getTop(), parentBox.height())) :
					Stream.<Size.Pixel>empty()
			)
			.flatMap(s -> s)
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
	}

	@Override
	public void render(RelativeBox element, Graphics2D graphics2D, FinalizedBox finalizedBox, FinalizedBox parentBox) {
		renderingUtilService.renderBox(graphics2D, element, finalizedBox);
		renderingUtilService.renderBorder(graphics2D, element, finalizedBox, parentBox);
	}
}
