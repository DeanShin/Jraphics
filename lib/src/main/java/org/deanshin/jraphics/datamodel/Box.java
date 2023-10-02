package org.deanshin.jraphics.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Box {
	private final Dimensions<?, ?> dimensions;
	private final Color color;
	private final List<Element> children;

	private Box(Dimensions<?, ?> dimensions, Color color, List<Element> children) {
		this.dimensions = dimensions;
		this.color = color;
		this.children = children;
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

	public List<Element> getChildren() {
		return children;
	}

	public static class Builder {
		private final ArrayList<Element> children;
		private Dimensions.Builder<?, ?> dimensions;
		private Color color;

		private Builder() {
			this.dimensions = Dimensions.builder();
			this.color = Color.WHITE;
			this.children = new ArrayList<>();
		}

		public Builder dimensions(Function<Dimensions.Builder<?, ?>, Dimensions.Builder<?, ?>> buildOperation) {
			this.dimensions = buildOperation.apply(this.dimensions);
			return this;
		}

		public Builder color(Color color) {
			this.color = color;
			return this;
		}

		public Builder children(Element... children) {
			this.children.addAll(Arrays.stream(children).toList());
			return this;
		}

		public Box build() {
			return new Box(dimensions.build(), color, children);
		}
	}
}
