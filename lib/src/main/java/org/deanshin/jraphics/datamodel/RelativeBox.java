package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class RelativeBox implements Element.HasSiblings, HasPadding, HasMargin, HasChildren, HasBorder, HasBox, Clickable {
	private final Box box;
	private final Offset padding;
	private final Border border;
	private final Offset margin;
	private final List<Element> children;
	private final Consumer<MouseEvent> onClick;
	private final Consumer<MouseEvent> onMouseEnter;
	private final Consumer<MouseEvent> onMouseExit;

	public RelativeBox(Box box, Offset padding, Border border, Offset margin, List<Element> children, Consumer<MouseEvent> onClick, Consumer<MouseEvent> onMouseEnter, Consumer<MouseEvent> onMouseExit) {
		this.box = box;
		this.padding = padding;
		this.border = border;
		this.margin = margin;
		this.children = children;
		this.onClick = onClick;
		this.onMouseEnter = onMouseEnter;
		this.onMouseExit = onMouseExit;
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

	@Override
	public Optional<Consumer<MouseEvent>> onClick() {
		return Optional.ofNullable(onClick);
	}

	@Override
	public Optional<Consumer<MouseEvent>> onMouseEnter() {
		return Optional.ofNullable(onMouseEnter);
	}

	@Override
	public Optional<Consumer<MouseEvent>> onMouseExit() {
		return Optional.ofNullable(onMouseExit);
	}

	public static class Builder {
		private final ArrayList<Element> children;
		private Box.Builder box;
		private Offset.Builder padding;
		private Border.Builder border;
		private Offset.Builder margin;
		private Consumer<MouseEvent> onClick;
		private Consumer<MouseEvent> onMouseEnter;
		private Consumer<MouseEvent> onMouseExit;

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

		public Builder onClick(Consumer<MouseEvent> onClick) {
			this.onClick = onClick;
			return this;
		}

		public Builder onMouseEnter(Consumer<MouseEvent> onMouseEnter) {
			this.onMouseEnter = onMouseEnter;
			return this;
		}

		public Builder onMouseExit(Consumer<MouseEvent> onMouseExit) {
			this.onMouseExit = onMouseExit;
			return this;
		}

		public RelativeBox build() {
			return new RelativeBox(box.build(), padding.build(), border.build(), margin.build(), children, onClick, onMouseEnter, onMouseExit);
		}
	}
}
