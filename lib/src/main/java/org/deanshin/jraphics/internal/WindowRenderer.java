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
			renderSide(graphics, Side.LEFT, hasBorder.getBorder().getLeft(), finalizedElementBox, parentBox);
			renderSide(graphics, Side.TOP, hasBorder.getBorder().getTop(), finalizedElementBox, parentBox);
			renderSide(graphics, Side.RIGHT, hasBorder.getBorder().getRight(), finalizedElementBox, parentBox);
			renderSide(graphics, Side.BOTTOM, hasBorder.getBorder().getBottom(), finalizedElementBox, parentBox);
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
				parentBox.x.add(sizeInPixels(absoluteBox.getX(), parentBox.width)),
				parentBox.y.add(sizeInPixels(absoluteBox.getY(), parentBox.height)),
				sizeInPixels(absoluteBox.getBox().getDimensions().getWidth(), parentBox.width),
				sizeInPixels(absoluteBox.getBox().getDimensions().getHeight(), parentBox.height),
				element
			);
		} else if (element instanceof RelativeBox relativeBox) {
			Size.Pixel x = getRelativeBoxFinalizedX(parentBox, relativeBox);
			Size.Pixel y = getRelativeBoxFinalizedY(parentBox, previousSiblingBox, relativeBox);

			return new FinalizedBox(
				x,
				y,
				sizeInPixels(relativeBox.getBox().getDimensions().getWidth(), parentBox.width),
				sizeInPixels(relativeBox.getBox().getDimensions().getHeight(), parentBox.height),
				element
			);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private Size.Pixel sizeInPixels(Size size, Size.Pixel parentSize) {
		if (size instanceof Size.Pixel pixel) {
			return pixel;
		} else if (size instanceof Size.Auto auto) {
			throw new UnsupportedOperationException();
		} else if (size instanceof Size.Percentage percentage) {
			return parentSize.multiply(percentage.getPercentage() / 100.0);
		} else if (size instanceof Size.Min min) {
			return min.getCandidates().stream()
				.map(candidate -> sizeInPixels(candidate, parentSize))
				.min(Comparator.comparingInt(Size.Pixel::getPixels))
				.orElseThrow();
		} else if (size instanceof Size.Max max) {
			return max.getCandidates().stream()
				.map(candidate -> sizeInPixels(candidate, parentSize))
				.max(Comparator.comparingInt(Size.Pixel::getPixels))
				.orElseThrow();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private void renderSide(
		Graphics2D graphics,
		Side side,
		Border.BorderSide borderSide,
		FinalizedBox bounds,
		FinalizedBox parent
	) {
		graphics.setColor(borderSide.getColor().toAwtColor());

		Size.Pixel x = bounds.x;
		Size.Pixel y = bounds.y;
		if (side == Side.RIGHT) {
			x = bounds.x.add(bounds.width).subtract(sizeInPixels(borderSide.getSize(), parent.width));
		} else if (side == Side.BOTTOM) {
			y = bounds.y.add(bounds.height).subtract(sizeInPixels(borderSide.getSize(), parent.height));
		}

		Size.Pixel width = bounds.width;
		Size.Pixel height = bounds.height;
		if (side == Side.LEFT || side == Side.RIGHT) {
			width = sizeInPixels(borderSide.getSize(), parent.width);
		} else {
			height = sizeInPixels(borderSide.getSize(), parent.height);
		}

		graphics.fillRect(x.getPixels(), y.getPixels(), width.getPixels(), height.getPixels());
	}

	private Size.Pixel getRelativeBoxFinalizedX(FinalizedBox parentBox, RelativeBox relativeBox) {
		return Stream.of(
				Stream.of(parentBox.x.add(sizeInPixels(relativeBox.getMargin().getLeft(), parentBox.width))),
				parentBox.element() instanceof HasPadding hasPadding ?
					Stream.of(sizeInPixels(hasPadding.getPadding().getLeft(), parentBox.width)) :
					Stream.<Size.Pixel>empty()
			)
			.flatMap(s -> s)
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
	}

	private Size.Pixel getRelativeBoxFinalizedY(FinalizedBox parentBox, FinalizedBox previousSiblingBox, RelativeBox relativeBox) {
		return Stream.of(
				previousSiblingBox == null ?
					Stream.<Size.Pixel>empty() :
					Stream.of(
						previousSiblingBox.y.add(previousSiblingBox.height)
							.add(previousSiblingBox.element instanceof HasMargin hasMargin ?
								sizeInPixels(hasMargin.getMargin().getBottom(), parentBox.height) :
								Size.ZERO
							),
						previousSiblingBox.y.add(previousSiblingBox.height)
							.add(sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height))
					),
				Stream.of(parentBox.y.add(sizeInPixels(relativeBox.getMargin().getTop(), parentBox.height))),
				parentBox.element() instanceof HasPadding hasPadding ?
					Stream.of(sizeInPixels(hasPadding.getPadding().getTop(), parentBox.height)) :
					Stream.<Size.Pixel>empty()
			)
			.flatMap(s -> s)
			.max(Comparator.comparingInt(Size.Pixel::getPixels))
			.orElseThrow();
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
