package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import javax.annotation.Nullable;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class WindowRenderer implements IWindowRenderer {
	public void render(Window window, Graphics2D graphics) {
		FinalizedBox finalizedWindowBox = new FinalizedBox(
			Size.ZERO,
			Size.ZERO,
			window.getDimensions().getWidth(),
			window.getDimensions().getHeight(),
			null
		);
		renderChildren(graphics, finalizedWindowBox, window.getChildren());
	}

	private void renderChildren(Graphics2D graphics, FinalizedBox parentBox, List<Element> children) {
		for (Element child : children) {
			if (child instanceof AbsoluteBox) {
				renderElement(graphics, child, parentBox, null);
			}
		}

		FinalizedBox prev = null;
		for (Element child : children) {
			if (!(child instanceof AbsoluteBox)) {
				prev = renderElement(graphics, child, parentBox, prev);
			}
		}
	}

	private FinalizedBox renderElement(
		Graphics2D graphics,
		Element element,
		FinalizedBox parentBox,
		@Nullable FinalizedBox previousBox
	) {
		FinalizedBox finalizedElementBox = getFinalizedBox(
			element,
			parentBox,
			previousBox
		);
		if (element instanceof HasBox hasBox) {
			graphics.setColor(hasBox.getBox().getColor().toAwtColor());
			graphics.fillRect(
				finalizedElementBox.x.getPixels(),
				finalizedElementBox.y.getPixels(),
				finalizedElementBox.width.getPixels(),
				finalizedElementBox.height.getPixels()
			);
		}
		if (element instanceof HasBorder hasBorder) {
			renderSide(graphics, Side.LEFT, hasBorder.getBorder().getLeft(), finalizedElementBox);
			renderSide(graphics, Side.TOP, hasBorder.getBorder().getTop(), finalizedElementBox);
			renderSide(graphics, Side.RIGHT, hasBorder.getBorder().getRight(), finalizedElementBox);
			renderSide(graphics, Side.BOTTOM, hasBorder.getBorder().getBottom(), finalizedElementBox);
		}
		if (element instanceof HasChildren hasChildren) {
			renderChildren(graphics, finalizedElementBox, hasChildren.getChildren());
		}

		return finalizedElementBox;
	}

	private FinalizedBox getFinalizedBox(
		Element element,
		FinalizedBox parentBox,
		@Nullable FinalizedBox previousSiblingBox
	) {
		if (element instanceof AbsoluteBox absoluteBox) {
			return new FinalizedBox(
				parentBox.x.add((Size.Pixel) absoluteBox.getX()),
				parentBox.y.add((Size.Pixel) absoluteBox.getY()),
				(Size.Pixel) absoluteBox.getBox().getDimensions().getWidth(),
				(Size.Pixel) absoluteBox.getBox().getDimensions().getHeight(),
				element
			);
		} else if (element instanceof RelativeBox relativeBox) {
			Size.Pixel x = Stream.of(
					Stream.of(parentBox.x.add((Size.Pixel) relativeBox.getMargin().getLeft())),
					parentBox.element() instanceof HasPadding hasPadding ?
						Stream.of((Size.Pixel) hasPadding.getPadding().getLeft()) :
						Stream.<Size.Pixel>empty()
				)
				.flatMap(s -> s)
				.max(Comparator.comparingInt(Size.Pixel::getPixels))
				.orElseThrow();

			Size.Pixel y = Stream.of(
					previousSiblingBox == null ?
						Stream.<Size.Pixel>empty() :
						Stream.of(
							previousSiblingBox.y.add(previousSiblingBox.height)
								.add(previousSiblingBox.element instanceof HasMargin hasMargin ?
									(Size.Pixel) hasMargin.getMargin().getBottom() :
									Size.ZERO
								),
							previousSiblingBox.y.add(previousSiblingBox.height).add((Size.Pixel) relativeBox.getMargin().getTop())
						),
					Stream.of(parentBox.y.add((Size.Pixel) relativeBox.getMargin().getTop())),
					parentBox.element() instanceof HasPadding hasPadding ?
						Stream.of((Size.Pixel) hasPadding.getPadding().getTop()) :
						Stream.<Size.Pixel>empty()
				)
				.flatMap(s -> s)
				.max(Comparator.comparingInt(Size.Pixel::getPixels))
				.orElseThrow();

			return new FinalizedBox(
				x,
				y,
				(Size.Pixel) relativeBox.getBox().getDimensions().getWidth(),
				(Size.Pixel) relativeBox.getBox().getDimensions().getHeight(),
				element
			);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private void renderSide(Graphics2D graphics, Side side, Border.BorderSide borderSide, FinalizedBox bounds) {
		graphics.setColor(borderSide.getColor().toAwtColor());

		Size.Pixel x = bounds.x;
		Size.Pixel y = bounds.y;
		if (side == Side.RIGHT) {
			x = bounds.x.add(bounds.width).subtract((Size.Pixel) borderSide.getSize());
		} else if (side == Side.BOTTOM) {
			y = bounds.y.add(bounds.width).subtract((Size.Pixel) borderSide.getSize());
		}

		Size.Pixel width = bounds.width;
		Size.Pixel height = bounds.height;
		if (side == Side.LEFT || side == Side.RIGHT) {
			width = (Size.Pixel) borderSide.getSize();
		} else {
			height = (Size.Pixel) borderSide.getSize();
		}

		graphics.fillRect(x.getPixels(), y.getPixels(), width.getPixels(), height.getPixels());
	}

	private enum Side {
		LEFT,
		TOP,
		RIGHT,
		BOTTOM
	}

	private record FinalizedBox(Size.Pixel x, Size.Pixel y, Size.Pixel width, Size.Pixel height,
	                            @Nullable Element element) {

	}
}
