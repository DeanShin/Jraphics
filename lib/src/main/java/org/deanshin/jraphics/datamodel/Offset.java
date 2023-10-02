package org.deanshin.jraphics.datamodel;

public class Offset {
	private final Size left;
	private final Size top;
	private final Size right;
	private final Size bottom;

	private Offset(Size left, Size top, Size right, Size bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public static Builder builder() {
		return new Builder();
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

		public Builder left(Size left) {
			this.left = left;
			return this;
		}

		public Builder top(Size top) {
			this.top = top;
			return this;
		}

		public Builder right(Size right) {
			this.right = right;
			return this;
		}

		public Builder bottom(Size bottom) {
			this.bottom = bottom;
			return this;
		}

		public Builder all(Size all) {
			return this.left(all).top(all).right(all).bottom(all);
		}

		public Offset build() {
			return new Offset(this.left, this.top, this.right, this.bottom);
		}
	}
}
