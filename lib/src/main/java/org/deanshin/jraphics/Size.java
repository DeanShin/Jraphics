package org.deanshin.jraphics;

import com.google.common.base.Preconditions;

/**
 * A class that represents the width and height of an object in integer precision.
 */
public class Size {
    private final int width;
    private final int height;

    public Size(int width, int height) {
        Preconditions.checkState(width >= 0, "width must be greater than 0.");
        Preconditions.checkState(height >= 0, "height must be greater than 0.");
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected java.awt.Dimension toAWTDimension() {
        return new java.awt.Dimension(width, height);
    }
}
