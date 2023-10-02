package org.deanshin.jraphics.datamodel;

public class Color {
    public static Color BLACK = new Color(0, 0, 0);
    public static Color WHITE = new Color(255, 255, 255);
    public static Color CLEAR = new Color(0, 0, 0, 0);

    private final int red;
    private final int green;
    private final int blue;
    private final double alpha;

    public Color(int red, int green, int blue) {
        this(red, green, blue, 1);
    }

    public Color(int red, int green, int blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(red, green, blue, (int) alpha * 255);
    }
}
