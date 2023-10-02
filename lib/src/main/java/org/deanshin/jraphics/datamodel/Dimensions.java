package org.deanshin.jraphics.datamodel;

public class Dimensions<WIDTH extends Size, HEIGHT extends Size> {
	private final WIDTH width;
	private final HEIGHT height;


	public Dimensions(WIDTH width, HEIGHT height) {
		this.width = width;
		this.height = height;
	}

	public static Builder<Size.Auto, Size.Auto> builder() {
		return new Builder<>();
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


	public static class Builder<WIDTH extends Size, HEIGHT extends Size> {
		private Size width;
		private Size height;

		private Builder() {
			this.width = Size.auto();
			this.height = Size.auto();
		}

		@SuppressWarnings("unchecked")
		public <NEW_WIDTH extends Size> Builder<NEW_WIDTH, HEIGHT> width(NEW_WIDTH width) {
			this.width = width;
			return (Builder<NEW_WIDTH, HEIGHT>) this;
		}

		@SuppressWarnings("unchecked")
		public <NEW_HEIGHT extends Size> Builder<WIDTH, NEW_HEIGHT> height(NEW_HEIGHT height) {
			this.height = height;
			return (Builder<WIDTH, NEW_HEIGHT>) this;
		}

		@SuppressWarnings("unchecked")
		public Dimensions<WIDTH, HEIGHT> build() {
			return new Dimensions<>((WIDTH) width, (HEIGHT) height);
		}
	}
}
