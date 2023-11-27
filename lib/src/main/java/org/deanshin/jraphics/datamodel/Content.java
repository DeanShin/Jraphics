package org.deanshin.jraphics.datamodel;

import java.util.function.Function;

/**
 * The container for the content of the box.
 */
public class Content {
	private final Dimensions<?, ?> dimensions;
	private final Color color;

	private Content(Dimensions<?, ?> dimensions, Color color) {
		this.dimensions = dimensions;
		this.color = color;
	}

	/**
	 * Create a new Content using the Builder design pattern.
	 *
	 * @return The builder for the Content.
	 */
	public static Builder builder() {
		return new Builder();
	}

	public Dimensions<?, ?> getDimensions() {
		return dimensions;
	}

	public Color getColor() {
		return color;
	}

	public static class Builder {
		private Dimensions.Builder<?, ?> dimensions;
		private Color color;

		private Builder() {
			this.dimensions = Dimensions.builder();
			this.color = Color.WHITE;
		}

		public Builder dimensions(Function<Dimensions.Builder<?, ?>, Dimensions.Builder<?, ?>> buildOperation) {
			this.dimensions = buildOperation.apply(this.dimensions);
			return this;
		}

		public Builder color(Color color) {
			this.color = color;
			return this;
		}

		/**
		 * Finalize construction of the Content
		 *
		 * @return the Content
		 */
		public Content build() {
			return new Content(dimensions.build(), color);
		}
	}
}
