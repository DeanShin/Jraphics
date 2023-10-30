package org.deanshin.jraphics.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class RelativeBox implements Element.HasSiblings, HasPadding, HasMargin, HasChildren, HasBorder, HasBox {
	private final Box box;
	private final Offset padding;
	private final Border border;
	private final Offset margin;

	private final List<Element> children;

	public RelativeBox(Box box, Offset padding, Border border, Offset margin, List<Element> children) {
		this.box = box;
		this.padding = padding;
		this.border = border;
		this.margin = margin;
		this.children = children;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Box getBox() {
		return box;
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


	@Override
	public List<Element> getChildren() {
		return children;
	}

	public static class Builder {
		private final ArrayList<Element> children;
		private Box.Builder box;
		private Offset.Builder padding;
		private Border.Builder border;
		private Offset.Builder margin;

		public Builder() {
			this.box = Box.builder();
			this.padding = Offset.builder();
			this.border = Border.builder();
			this.margin = Offset.builder();
			this.children = new ArrayList<>();
		}

		public Builder box(Function<Box.Builder, Box.Builder> buildOperation) {
			this.box = buildOperation.apply(this.box);
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

		public Builder children(Element.HasSiblings... children) {
			this.children.addAll(Arrays.stream(children).toList());
			return this;
		}

		public Builder child(Element.HasNoSiblings child) {
			this.children.clear();
			this.children.add(child);
			return this;
		}

		public RelativeBox build() {
			return new RelativeBox(box.build(), padding.build(), border.build(), margin.build(), children);
		}
	}
}
