package org.deanshin.jraphics;

public class Dimensions<WIDTH extends Size, HEIGHT extends Size> {
    private final WIDTH width;
    private final HEIGHT height;

    public Dimensions(WIDTH width, HEIGHT height) {
        this.width = width;
        this.height = height;
    }

    public WIDTH getWidth() {
        return width;
    }

    public HEIGHT getHeight() {
        return height;
    }

    public <NEW_WIDTH extends Size> Dimensions<NEW_WIDTH, HEIGHT> withWidth(NEW_WIDTH newWidth) {
        return new Dimensions<>(newWidth, height);
    }

    public <NEW_HEIGHT extends Size> Dimensions<WIDTH, NEW_HEIGHT> withHeight(NEW_HEIGHT newHeight) {
        return new Dimensions<>(width, newHeight);
    }
}
