package org.deanshin.jraphics;

public class Rectangle<WIDTH extends Size, HEIGHT extends Size> {
    private final WIDTH width;
    private final HEIGHT height;

    public Rectangle(WIDTH width, HEIGHT height) {
        this.width = width;
        this.height = height;
    }

    public WIDTH getWidth() {
        return width;
    }

    public HEIGHT getHeight() {
        return height;
    }

    public <NEW_WIDTH extends Size> Rectangle<NEW_WIDTH, HEIGHT> withWidth(NEW_WIDTH newWidth) {
        return new Rectangle<>(newWidth, height);
    }

    public <NEW_HEIGHT extends Size> Rectangle<WIDTH, NEW_HEIGHT> withHeight(NEW_HEIGHT newHeight) {
        return new Rectangle<>(width, newHeight);
    }
}
