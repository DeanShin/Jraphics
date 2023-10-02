package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import java.awt.Graphics2D;

public class WindowRenderer implements IWindowRenderer {
    public void render(Window window, Graphics2D graphics) {
        FinalizedBox finalizedWindowBox = new FinalizedBox(
                Size.ZERO,
                Size.ZERO,
                window.getDimensions().getWidth(),
                window.getDimensions().getHeight()
        );
        for(Element child : window.getChildren()) {
            renderElement(graphics, child, finalizedWindowBox);
        }
    }

    private void renderElement(Graphics2D graphics, Element element, FinalizedBox parentBox) {
        AbsoluteBox absoluteBox = (AbsoluteBox) element;
        FinalizedBox finalizedElementBox = new FinalizedBox(
                parentBox.x.add((Size.Pixel) absoluteBox.getX()),
                parentBox.y.add((Size.Pixel) absoluteBox.getY()),
                (Size.Pixel) absoluteBox.getBox().getDimensions().getWidth(),
                (Size.Pixel) absoluteBox.getBox().getDimensions().getHeight()
        );
        graphics.setColor(absoluteBox.getBox().getColor().toAwtColor());
        graphics.fillRect(
                finalizedElementBox.x.getPixels(),
                finalizedElementBox.y.getPixels(),
                finalizedElementBox.width.getPixels(),
                finalizedElementBox.height.getPixels()
        );
        renderSide(graphics, Side.LEFT, absoluteBox.getBorder().getLeft(), finalizedElementBox);
        renderSide(graphics, Side.TOP, absoluteBox.getBorder().getTop(), finalizedElementBox);
        renderSide(graphics, Side.RIGHT, absoluteBox.getBorder().getRight(), finalizedElementBox);
        renderSide(graphics, Side.BOTTOM, absoluteBox.getBorder().getBottom(), finalizedElementBox);

        for(Element child : element.getChildren()) {
            renderElement(graphics, child, finalizedElementBox);
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
        if(side == Side.LEFT || side == Side.RIGHT) {
            width = (Size.Pixel) borderSide.getSize();
        } else {
            height = (Size.Pixel) borderSide.getSize();
        }

        graphics.fillRect(x.getPixels(), y.getPixels(), width.getPixels(), height.getPixels());
    }

    private record FinalizedBox(Size.Pixel x, Size.Pixel y, Size.Pixel width, Size.Pixel height) {

    }

    private enum Side {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }
}
