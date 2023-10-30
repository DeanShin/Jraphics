package org.deanshin.jraphics.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AbsoluteBox implements Element.HasSiblings, HasPadding, HasChildren, HasBorder, HasBox {
	private final Box box;
	private final Border border;
	private final Size x;
	private final Size y;
	private final Offset padding;
	private final List<Element> children;

	private AbsoluteBox(Box box, Border border, Size x, Size y, Offset padding, List<Element> children) {
		this.box = box;
		this.border = border;
		this.x = x;
		this.y = y;
		this.padding = padding;
		this.children = children;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Box getBox() {
		return box;
	}

	public Border getBorder() {
		return border;
	}

	public Size getX() {
		return x;
	}

	public Size getY() {
		return y;
	}

	@Override
	public List<Element> getChildren() {
		return children;
	}

	@Override
	public Offset getPadding() {
		return padding;
	}

	public static class Builder {
		private final ArrayList<Element> children;
		private Box.Builder box;
		private Border.Builder border;
		private Size x;
		private Size y;
		private Offset.Builder padding;

		private Builder() {
			this.box = Box.builder();
			this.border = Border.builder();
			this.x = Size.ZERO;
			this.y = Size.ZERO;
			this.padding = Offset.builder();
			this.children = new ArrayList<>();
		}

		public Builder box(Function<Box.Builder, Box.Builder> buildOperation) {
			box = buildOperation.apply(box);
			return this;
		}

		public Builder border(Function<Border.Builder, Border.Builder> buildOperation) {
			border = buildOperation.apply(border);
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

		public Builder padding(Function<Offset.Builder, Offset.Builder> buildOperation) {
			padding = buildOperation.apply(padding);
			return this;
		}

		public Builder children(Element.HasSiblings... children) {
			this.children.addAll(Arrays.stream(children).toList());
			return this;
		}

		public Builder child(Element.HasNoSiblings child) {
			this.children.clear();
			this.children.add(child);
			return this;
		}

		public AbsoluteBox build() {
			return new AbsoluteBox(box.build(), border.build(), x, y, padding.build(), children);
		}
	}
}
