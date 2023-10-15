package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Border;
import org.deanshin.jraphics.datamodel.HasBorder;
import org.deanshin.jraphics.datamodel.HasBox;
import org.deanshin.jraphics.datamodel.Size;

import java.awt.Graphics2D;
import java.util.Comparator;

class RenderingUtilServiceImpl implements RenderingUtilService {
	public Size.Pixel sizeInPixels(Size size, Size.Pixel parentSize) {
		if (size instanceof Size.Pixel pixel) {
			return pixel;
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

	public void renderBox(Graphics2D graphics, HasBox hasBox, FinalizedBox finalizedBox) {
		graphics.setColor(hasBox.getBox().getColor().toAwtColor());
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
			x = bounds.x().add(bounds.width()).subtract(sizeInPixels(borderSide.getSize(), parent.width()));
		} else if (side == Side.BOTTOM) {
			y = bounds.y().add(bounds.height()).subtract(sizeInPixels(borderSide.getSize(), parent.height()));
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
