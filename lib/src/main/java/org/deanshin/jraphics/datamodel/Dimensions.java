package org.deanshin.jraphics.datamodel;

public class Dimensions<WIDTH extends Size, HEIGHT extends Size> {
	private final WIDTH width;
	private final HEIGHT height;


	public Dimensions(WIDTH width, HEIGHT height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Create a new Dimensions using the Builder design pattern.
	 *
	 * @return The builder for the Dimensions.
	 */
	public static Builder<Size.Pixel, Size.Pixel> builder() {
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
			this.width = Size.pixel(100);
			this.height = Size.pixel(100);
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

		/**
		 * Finalize construction of the Dimensions
		 *
		 * @return the Dimensions
		 */
		@SuppressWarnings("unchecked")
		public Dimensions<WIDTH, HEIGHT> build() {
			return new Dimensions<>((WIDTH) width, (HEIGHT) height);
		}
	}
}
