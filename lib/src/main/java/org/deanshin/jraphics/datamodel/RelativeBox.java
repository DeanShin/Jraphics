package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The relative box mimics elements in the CSS box model.
 * <p>
 * Each relative box contains the main content box, the surrounding padding, the border, and the margin.
 * </p>
 * <p>
 * The main content box (see the box field) is the container for the children of the relative box. For example, if you
 * have a Text element within the relative box, the Text element's top left corner will be aligned with the top left of
 * the content box.
 * </p>
 * <p>
 * The padding acts as spacing between the content box and the edge of the element.
 * </p>
 * <p>
 * The border surrounds the padding and serves to visually distinguish boxes from each other if necessary.
 * </p>
 * <p>
 * The margin is used to add a gap between relative boxes. The margin field specifies how far away another relative box
 * must be from the current relative box in each direction.
 * </p>
 */
public class RelativeBox implements Element.HasSiblings, HasPadding, HasMargin, HasChildren, HasFlow, HasBorder, HasContent, Clickable {
	private final Content content;
	private final Offset padding;
	private final Border border;
	private final Offset margin;
	private final List<Element> children;
	private final ElementFlow flow;
	private final Consumer<MouseEvent> onClick;
	private final Consumer<MouseEvent> onMouseEnter;
	private final Consumer<MouseEvent> onMouseExit;

	public RelativeBox(Content content, Offset padding, Border border, Offset margin, List<Element> children, ElementFlow flow, Consumer<MouseEvent> onClick, Consumer<MouseEvent> onMouseEnter, Consumer<MouseEvent> onMouseExit) {
		this.content = content;
		this.padding = padding;
		this.border = border;
		this.margin = margin;
		this.children = children;
		this.flow = flow;
		this.onClick = onClick;
		this.onMouseEnter = onMouseEnter;
		this.onMouseExit = onMouseExit;
	}

	/**
	 * Create a new RelativeBox using the Builder design pattern.
	 *
	 * @return The builder for the RelativeBox.
	 */
	public static Builder builder() {
		return new Builder();
	}

	public Content getContent() {
		return content;
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
	public ElementFlow getFlow() {
		return flow;
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
		private Offset.Builder padding;
		private Border.Builder border;
		private Offset.Builder margin;
		private ElementFlow flow;
		private Consumer<MouseEvent> onClick;
		private Consumer<MouseEvent> onMouseEnter;
		private Consumer<MouseEvent> onMouseExit;

		public Builder() {
			this.content = Content.builder();
			this.padding = Offset.builder();
			this.border = Border.builder();
			this.margin = Offset.builder();
			this.children = new ArrayList<>();
			this.flow = ElementFlow.VERTICAL;
		}

		/**
		 * Specify the content of the relative box.
		 *
		 * @return The builder
		 */
		public Builder content(Function<Content.Builder, Content.Builder> buildOperation) {
			this.content = buildOperation.apply(this.content);
			return this;
		}

		/**
		 * Specify the border of the relative box.
		 *
		 * @return The builder
		 */
		public Builder border(Function<Border.Builder, Border.Builder> buildOperation) {
			this.border = buildOperation.apply(border);
			return this;
		}

		/**
		 * Specify the margin of the relative box.
		 *
		 * @return The builder
		 */
		public Builder margin(Function<Offset.Builder, Offset.Builder> buildOperation) {
			this.margin = buildOperation.apply(margin);
			return this;
		}

		/**
		 * Specify the padding of the relative box.
		 *
		 * @return The builder
		 */
		public Builder padding(Function<Offset.Builder, Offset.Builder> buildOperation) {
			padding = buildOperation.apply(padding);
			return this;
		}

		/**
		 * Specify the children of the relative box.
		 *
		 * @return The builder
		 */
		public Builder children(Element.HasSiblings... children) {
			this.children.addAll(Arrays.stream(children).toList());
			return this;
		}

		/**
		 * Specify the child of the relative box.
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
		 * Add a consumer that is called when the relative box is clicked
		 *
		 * @return The builder
		 */
		public Builder onClick(Consumer<MouseEvent> onClick) {
			this.onClick = onClick;
			return this;
		}

		/**
		 * Add a consumer that is called when the mouse enters the bounds of the relative box
		 *
		 * @return The builder
		 */
		public Builder onMouseEnter(Consumer<MouseEvent> onMouseEnter) {
			this.onMouseEnter = onMouseEnter;
			return this;
		}

		/**
		 * Add a consumer that is called when the mouse exits the bounds of the relative box
		 *
		 * @return The builder
		 */
		public Builder onMouseExit(Consumer<MouseEvent> onMouseExit) {
			this.onMouseExit = onMouseExit;
			return this;
		}

		/**
		 * Finalize construction of the RelativeBox
		 *
		 * @return the RelativeBox
		 */
		public RelativeBox build() {
			return new RelativeBox(content.build(), padding.build(), border.build(), margin.build(), children, flow, onClick, onMouseEnter, onMouseExit);
		}
	}
}
