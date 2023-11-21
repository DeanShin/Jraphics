package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbsoluteBox implements Element.HasSiblings, HasPadding, HasChildren, HasFlow, HasBorder, HasBox, Clickable {
	private final Box box;
	private final Border border;
	private final Size x;
	private final Size y;
	private final Offset padding;
	private final List<Element> children;
	private final ElementFlow flow;
	private final Consumer<MouseEvent> onClick;
	private final Consumer<MouseEvent> onMouseEnter;
	private final Consumer<MouseEvent> onMouseExit;

	private AbsoluteBox(Box box, Border border, Size x, Size y, Offset padding, List<Element> children, ElementFlow flow, Consumer<MouseEvent> onClick, Consumer<MouseEvent> onMouseEnter, Consumer<MouseEvent> onMouseExit) {
		this.box = box;
		this.border = border;
		this.x = x;
		this.y = y;
		this.padding = padding;
		this.children = children;
		this.flow = flow;
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
	public ElementFlow getFlow() {
		return flow;
	}

	@Override
	public Offset getPadding() {
		return padding;
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
		private Border.Builder border;
		private Size x;
		private Size y;
		private Offset.Builder padding;
		private ElementFlow flow;
		private Consumer<MouseEvent> onClick;
		private Consumer<MouseEvent> onMouseEnter;
		private Consumer<MouseEvent> onMouseExit;

		private Builder() {
			this.box = Box.builder();
			this.border = Border.builder();
			this.x = Size.ZERO;
			this.y = Size.ZERO;
			this.padding = Offset.builder();
			this.children = new ArrayList<>();
			this.flow = ElementFlow.VERTICAL;
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

		public Builder flow(ElementFlow flow) {
			this.flow = flow;
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

		public AbsoluteBox build() {
			return new AbsoluteBox(box.build(), border.build(), x, y, padding.build(), children, flow, onClick, onMouseEnter, onMouseExit);
		}
	}
}
