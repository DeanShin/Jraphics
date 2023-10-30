package org.deanshin.jraphics.datamodel;

import java.util.function.Function;

public class Box {
	private final Dimensions<?, ?> dimensions;
	private final Color color;

	private Box(Dimensions<?, ?> dimensions, Color color) {
		this.dimensions = dimensions;
		this.color = color;
	}

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


		public Box build() {
			return new Box(dimensions.build(), color);
		}
	}
}
