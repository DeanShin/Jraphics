package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The absolute box mimics elements in the CSS box model.
 * <p>
 * Each absolute box contains the main content box, the surrounding padding, and the border.
 * </p>
 * <p>
 * The main content box (see the box field) is the container for the children of the absolute box. For example, if you
 * have a Text element within the absolute box, the Text element's top left corner will be aligned with the top left of
 * the content box.
 * </p>
 * <p>
 * The padding acts as spacing between the content box and the edge of the element.
 * </p>
 * <p>
 * The border surrounds the padding and serves to visually distinguish boxes from each other if necessary.
 * </p>
 */
public class AbsoluteBox implements Element.HasSiblings, HasPadding, HasChildren, HasFlow, HasBorder, HasContent, Clickable {
	private final Content content;
	private final Border border;
	private final Size x;
	private final Size y;
	private final Offset padding;
	private final List<Element> children;
	private final ElementFlow flow;
	private final Consumer<MouseEvent> onClick;
	private final Consumer<MouseEvent> onMouseEnter;
	private final Consumer<MouseEvent> onMouseExit;

	private AbsoluteBox(Content content, Border border, Size x, Size y, Offset padding, List<Element> children, ElementFlow flow, Consumer<MouseEvent> onClick, Consumer<MouseEvent> onMouseEnter, Consumer<MouseEvent> onMouseExit) {
		this.content = content;
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

	/**
	 * Create a new AbsoluteBox using the Builder design pattern.
	 *
	 * @return The builder for the AbsoluteBox.
	 */
	public static Builder builder() {
		return new Builder();
	}

	public Content getContent() {
		return content;
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
		private Content.Builder content;
		private Border.Builder border;
		private Size x;
		private Size y;
		private Offset.Builder padding;
		private ElementFlow flow;
		private Consumer<MouseEvent> onClick;
		private Consumer<MouseEvent> onMouseEnter;
		private Consumer<MouseEvent> onMouseExit;

		private Builder() {
			this.content = Content.builder();
			this.border = Border.builder();
			this.x = Size.ZERO;
			this.y = Size.ZERO;
			this.padding = Offset.builder();
			this.children = new ArrayList<>();
			this.flow = ElementFlow.VERTICAL;
		}

		/**
		 * Specify the main container for the content of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder content(Function<Content.Builder, Content.Builder> buildOperation) {
			content = buildOperation.apply(content);
			return this;
		}

		/**
		 * Specify the border of the absolute box. The border is wrapped around the padding of the box.
		 *
		 * @return The builder
		 */
		public Builder border(Function<Border.Builder, Border.Builder> buildOperation) {
			border = buildOperation.apply(border);
			return this;
		}

		/**
		 * Specify the x position of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder x(Size x) {
			this.x = x;
			return this;
		}

		/**
		 * Specify the y position of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder y(Size y) {
			this.y = y;
			return this;
		}

		/**
		 * Specify the padding of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder padding(Function<Offset.Builder, Offset.Builder> buildOperation) {
			padding = buildOperation.apply(padding);
			return this;
		}

		/**
		 * Specify the children of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder children(Element.HasSiblings... children) {
			this.children.addAll(Arrays.stream(children).toList());
			return this;
		}

		/**
		 * Specify the child of the absolute box.
		 *
		 * @return The builder
		 */
		public Builder child(Element.HasNoSiblings child) {
			this.children.clear();
			this.children.add(child);
			return this;
		}

		/**
		 * Specify the direction that the child elements are laid out.
		 *
		 * @return The builder
		 */
		public Builder flow(ElementFlow flow) {
			this.flow = flow;
			return this;
		}

		/**
		 * Add a consumer that is called when the absolute box is clicked
		 *
		 * @return The builder
		 */
		public Builder onClick(Consumer<MouseEvent> onClick) {
			this.onClick = onClick;
			return this;
		}

		/**
		 * Add a consumer that is called when the mouse enters the bounds of the absolute box
		 *
		 * @return The builder
		 */
		public Builder onMouseEnter(Consumer<MouseEvent> onMouseEnter) {
			this.onMouseEnter = onMouseEnter;
			return this;
		}

		/**
		 * Add a consumer that is called when the mouse exits the bounds of the absolute box
		 *
		 * @return The builder
		 */
		public Builder onMouseExit(Consumer<MouseEvent> onMouseExit) {
			this.onMouseExit = onMouseExit;
			return this;
		}

		/**
		 * End construction of the AbsoluteBox.
		 *
		 * @return The absolute box
		 */
		public AbsoluteBox build() {
			return new AbsoluteBox(content.build(), border.build(), x, y, padding.build(), children, flow, onClick, onMouseEnter, onMouseExit);
		}
	}
}
