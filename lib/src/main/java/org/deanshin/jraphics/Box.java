package org.deanshin.jraphics;

public class Box {
    private final Dimensions<?, ?> content;
    private final Offset padding;
    private final Border border;
    private final Offset margin;

    public Box(Dimensions<?, ?> content, Offset padding, Border border, Offset margin) {
        this.content = content;
        this.padding = padding;
        this.border = border;
        this.margin = margin;
    }
}
