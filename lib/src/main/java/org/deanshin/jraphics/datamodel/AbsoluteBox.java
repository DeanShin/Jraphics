package org.deanshin.jraphics.datamodel;

import java.util.List;
import java.util.function.Function;

public class AbsoluteBox implements Element {
    private final Box box;
    private final Border border;
    private final Size x;
    private final Size y;

    private AbsoluteBox(Box box, Border border, Size x, Size y) {
        this.box = box;
        this.border = border;
        this.x = x;
        this.y = y;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Box getBox() {
        return box;
    }

    public Border getBorder() {
        return border;
    }

    public Size getX() {
        return x;
    }

    public Size getY() {
        return y;
    }

    @Override
    public List<Element> getChildren() {
        return box.getChildren();
    }

    public static class Builder {
        public Box.Builder box;
        public Border.Builder border;
        public Size x;
        public Size y;

        private Builder() {
            box = Box.builder();
            border = Border.builder();
            x = Size.ZERO;
            y = Size.ZERO;
        }

        public Builder box(Function<Box.Builder, Box.Builder> buildOperation) {
            box = buildOperation.apply(box);
            return this;
        }

        public Builder border(Function<Border.Builder, Border.Builder> buildOperation) {
            border = buildOperation.apply(border);
            return this;
        }

        public Builder x(Size x) {
            this.x = x;
            return this;
        }

        public Builder y(Size y) {
            this.y = y;
            return this;
        }

        public AbsoluteBox build() {
            return new AbsoluteBox(box.build(), border.build(), x, y);
        }
    }
}
