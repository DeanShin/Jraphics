package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import javax.annotation.Nullable;
import java.awt.Graphics2D;
import java.util.Comparator;

class RenderingUtilServiceImpl implements RenderingUtilService {
	public Size.Pixel sizeInPixels(Size size, Size.Pixel parentSize) {
		if (size instanceof Size.Pixel pixel) {
			return pixel;
		} else if (size instanceof Size.Percentage percentage) {
			return parentSize.times(percentage.getPercentage() / 100.0);
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

	@Override
	public FinalizedBox contentBox(FinalizedBox finalizedBox, FinalizedBox parentBox, @Nullable Element element) {
		Size.Pixel leftPadding = finalizedBox.element() instanceof HasPadding padding ? sizeInPixels(padding.getPadding().getLeft(), parentBox.width()) : Size.ZERO;
		Size.Pixel topPadding = finalizedBox.element() instanceof HasPadding padding ? sizeInPixels(padding.getPadding().getTop(), parentBox.height()) : Size.ZERO;
		Size.Pixel rightPadding = finalizedBox.element() instanceof HasPadding padding ? sizeInPixels(padding.getPadding().getRight(), parentBox.width()) : Size.ZERO;
		Size.Pixel bottomPadding = finalizedBox.element() instanceof HasPadding padding ? sizeInPixels(padding.getPadding().getBottom(), parentBox.height()) : Size.ZERO;
		return new FinalizedBox(
			finalizedBox.x().plus(leftPadding),
			finalizedBox.y().plus(topPadding),
			finalizedBox.width().minus(leftPadding).minus(rightPadding),
			finalizedBox.height().minus(topPadding).minus(bottomPadding),
			element
		);
	}

	public void renderBox(Graphics2D graphics, HasContent hasContent, FinalizedBox finalizedBox) {
		graphics.setColor(hasContent.getContent().getColor().toAwtColor());
		graphics.fillRect(
			finalizedBox.x().getPixels(),
			finalizedBox.y().getPixels(),
			finalizedBox.width().getPixels(),
			finalizedBox.height().getPixels()
		);
	}

	public void renderBorder(Graphics2D graphics, HasBorder hasBorder, FinalizedBox finalizedBox, FinalizedBox parentBox) {
		renderSide(graphics, Side.LEFT, hasBorder.getBorder().getLeft(), finalizedBox, parentBox);
		renderSide(graphics, Side.TOP, hasBorder.getBorder().getTop(), finalizedBox, parentBox);
		renderSide(graphics, Side.RIGHT, hasBorder.getBorder().getRight(), finalizedBox, parentBox);
		renderSide(graphics, Side.BOTTOM, hasBorder.getBorder().getBottom(), finalizedBox, parentBox);
	}

	private void renderSide(
		Graphics2D graphics,
		Side side,
		Border.BorderSide borderSide,
		FinalizedBox bounds,
		FinalizedBox parent
	) {
		graphics.setColor(borderSide.getColor().toAwtColor());

		Size.Pixel x = bounds.x();
		Size.Pixel y = bounds.y();
		if (side == Side.RIGHT) {
			x = bounds.x().plus(bounds.width()).minus(sizeInPixels(borderSide.getSize(), parent.width()));
		} else if (side == Side.BOTTOM) {
			y = bounds.y().plus(bounds.height()).minus(sizeInPixels(borderSide.getSize(), parent.height()));
		}

		Size.Pixel width = bounds.width();
		Size.Pixel height = bounds.height();
		if (side == Side.LEFT || side == Side.RIGHT) {
			width = sizeInPixels(borderSide.getSize(), parent.width());
		} else {
			height = sizeInPixels(borderSide.getSize(), parent.height());
		}

		graphics.fillRect(x.getPixels(), y.getPixels(), width.getPixels(), height.getPixels());
	}

	public enum Side {
		LEFT,
		TOP,
		RIGHT,
		BOTTOM
	}
}
