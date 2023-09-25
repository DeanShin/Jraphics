package org.deanshin.jraphics;

import java.util.function.Function;

public class Box {
    private final Dimensions<?, ?> content;
    private final Offset padding;
    private final Border border;
    private final Offset margin;

    private Box(Dimensions<?, ?> content, Offset padding, Border border, Offset margin) {
        this.content = content;
        this.padding = padding;
        this.border = border;
        this.margin = margin;
    }

    public static Box.Builder builder() {
        return new Builder();
    }

    public Dimensions<?, ?> getContent() {
        return content;
    }

    public Offset getPadding() {
        return padding;
    }

    public Border getBorder() {
        return border;
    }

    public Offset getMargin() {
        return margin;
    }

    public static class Builder {
        private Dimensions.Builder<?, ?> content;
        private Offset.Builder padding;
        private Border.Builder border;
        private Offset.Builder margin;

        public Builder() {
            this.content = Dimensions.builder();
            this.padding = Offset.builder();
            this.border = Border.builder();
            this.margin = Offset.builder();
        }

        public Builder content(Function<Dimensions.Builder<?, ?>, Dimensions.Builder<?, ?>> buildOperation) {
            this.content = buildOperation.apply(this.content);
            return this;
        }

        public Builder padding(Function<Offset.Builder, Offset.Builder> buildOperation) {
            this.padding = buildOperation.apply(padding);
            return this;
        }

        public Builder border(Function<Border.Builder, Border.Builder> buildOperation) {
            this.border = buildOperation.apply(border);
            return this;
        }

        public Builder margin(Function<Offset.Builder, Offset.Builder> buildOperation) {
            this.margin = buildOperation.apply(margin);
            return this;
        }

        public Box build() {
            return new Box(content.build(), padding.build(), border.build(), margin.build());
        }
    }
}
