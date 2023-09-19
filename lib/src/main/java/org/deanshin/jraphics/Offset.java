package org.deanshin.jraphics;

/**
 * A class that represents the CSS box model.
 */
public class Offset {
    private final Size left;
    private final Size top;
    private final Size right;
    private final Size bottom;

    public Builder builder() {
        return new Builder();
    }

    private Offset(Size left, Size top, Size right, Size bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Size getLeft() {
        return left;
    }

    public Size getTop() {
        return top;
    }

    public Size getRight() {
        return right;
    }

    public Size getBottom() {
        return bottom;
    }

    public static class Builder {
        private Size left;
        private Size top;
        private Size right;
        private Size bottom;

        private Builder() {
            this.left = Size.ZERO;
            this.top = Size.ZERO;
            this.right = Size.ZERO;
            this.bottom = Size.ZERO;
        }

        public Builder setLeft(Size left) {
            this.left = left;
            return this;
        }

        public Builder setTop(Size top) {
            this.top = top;
            return this;
        }

        public Builder setRight(Size right) {
            this.right = right;
            return this;
        }

        public Builder setBottom(Size bottom) {
            this.bottom = bottom;
            return this;
        }

        public Offset build() {
            return new Offset(this.left, this.top, this.right, this.bottom);
        }
    }
}
