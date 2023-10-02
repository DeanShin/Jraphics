package org.deanshin.jraphics.datamodel;

import java.util.function.Function;

public class AbsoluteBox implements Element {
    private final Box box;
    private final Size x;
    private final Size y;

    private AbsoluteBox(Box box, Size x, Size y) {
        this.box = box;
        this.x = x;
        this.y = y;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Box getBox() {
        return box;
    }

    public Size getX() {
        return x;
    }

    public Size getY() {
        return y;
    }

    public static class Builder {
        public Box.Builder box;
        public Size x;
        public Size y;

        private Builder() {
            box = Box.builder();
            x = Size.ZERO;
            y = Size.ZERO;
        }

        public Builder box(Function<Box.Builder, Box.Builder> buildOperation) {
            box = buildOperation.apply(box);
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

        public Builder build() {
            return this;
        }
    }
}
