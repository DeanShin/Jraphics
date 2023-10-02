package org.deanshin.jraphics.datamodel;

import java.util.List;
import java.util.function.Function;

public class Border {
	private final BorderSide left;
	private final BorderSide top;
	private final BorderSide right;
	private final BorderSide bottom;

	private Border(BorderSide left, BorderSide top, BorderSide right, BorderSide bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public static Builder builder() {
		return new Builder();
	}

	public BorderSide getLeft() {
		return left;
	}

	public BorderSide getTop() {
		return top;
	}

	public BorderSide getRight() {
		return right;
	}

	public BorderSide getBottom() {
		return bottom;
	}

	public static class Builder {
		private final List<BorderSide.Builder> all;
		private BorderSide.Builder left;
		private BorderSide.Builder top;
		private BorderSide.Builder right;
		private BorderSide.Builder bottom;

		private Builder() {
			this.left = BorderSide.builder();
			this.top = BorderSide.builder();
			this.right = BorderSide.builder();
			this.bottom = BorderSide.builder();
			this.all = List.of(this.left, this.top, this.right, this.bottom);
		}

		public Builder all(Function<BorderSide.Builder, BorderSide.Builder> buildOperation) {
			this.all.forEach(buildOperation::apply);
			return this;
		}

		public Builder left(Function<BorderSide.Builder, BorderSide.Builder> buildOperation) {
			this.left = buildOperation.apply(this.left);
			return this;
		}

		public Builder top(Function<BorderSide.Builder, BorderSide.Builder> buildOperation) {
			this.top = buildOperation.apply(this.top);
			return this;
		}

		public Builder right(Function<BorderSide.Builder, BorderSide.Builder> buildOperation) {
			this.right = buildOperation.apply(this.right);
			return this;
		}

		public Builder bottom(Function<BorderSide.Builder, BorderSide.Builder> buildOperation) {
			this.bottom = buildOperation.apply(this.bottom);
			return this;
		}

		public Border build() {
			return new Border(left.build(), top.build(), right.build(), bottom.build());
		}
	}

	public static class BorderSide {
		private final Size size;
		private final Color color;

		private BorderSide(Size size, Color color) {
			this.size = size;
			this.color = color;
		}

		public static Builder builder() {
			return new BorderSide.Builder();
		}

		public Size getSize() {
			return size;
		}

		public Color getColor() {
			return color;
		}

		public static class Builder {
			private Size size;
			private Color color;

			private Builder() {
				this.size = Size.pixel(0);
				this.color = Color.CLEAR;
			}

			public Builder size(Size size) {
				this.size = size;
				return this;
			}

			public Builder color(Color color) {
				this.color = color;
				return this;
			}

			public BorderSide build() {
				return new BorderSide(this.size, this.color);
			}
		}
	}
}
