package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import javax.annotation.Nullable;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowRendererImpl implements WindowRenderer {
	private final Map<Class<? extends Element>, Renderer<? extends Element>> renderers;
	private final MouseCollisionHandler mouseCollisionHandler;

	public WindowRendererImpl(Frame frame) {
		RenderingUtilService renderingUtilService = new RenderingUtilServiceImpl();
		this.renderers = Stream.of(
			new TextRenderer(renderingUtilService),
			new AbsoluteBoxRenderer(renderingUtilService),
			new RelativeBoxRenderer(renderingUtilService)
		).collect(Collectors.toMap(Renderer::getElementClass, r -> r));
		this.mouseCollisionHandler = new MouseCollisionHandler();
		frame.addMouseListener(mouseCollisionHandler);
	}

	public void render(Window window, Graphics2D graphics) {
		graphics.clearRect(0, 0, window.getDimensions().getWidth().getPixels(), window.getDimensions().getHeight().getPixels());
		FinalizedBox finalizedWindowBox = new FinalizedBox(
			Size.ZERO,
			Size.ZERO,
			window.getDimensions().getWidth(),
			window.getDimensions().getHeight(),
			null
		);
		mouseCollisionHandler.reset();
		renderChildren(graphics, finalizedWindowBox, window.getChildren(), ElementFlow.VERTICAL, null);
	}

	private FinalizedBox renderChildren(Graphics2D graphics, FinalizedBox parentBox, List<Element> children, ElementFlow flow, @Nullable FinalizedBox previousBox) {
		FinalizedBox prev = previousBox;
		for (Element child : children) {
			if (child instanceof AbsoluteBox) {
				// AbsoluteBoxes are rendered outside the normal element flow.
				renderElement(graphics, child, parentBox, flow, null);
			} else {
				prev = renderElement(graphics, child, parentBox, flow, prev);
			}
		}
		return prev;
	}

	private <T extends Element> FinalizedBox renderElement(
		Graphics2D graphics,
		T element,
		FinalizedBox parentBox,
		ElementFlow flow,
		@Nullable FinalizedBox previousBox
	) {
		if (element instanceof Component<?, ?> component) {
			return renderChildren(graphics, parentBox, component.getChildren(), flow, previousBox);
		}

		@SuppressWarnings("unchecked") Renderer<T> renderer = (Renderer<T>) renderers.get(element.getClass());
		FinalizedBox finalizedBox = renderer.getBounds(element, graphics, parentBox, previousBox, flow);
		renderer.render(element, graphics, finalizedBox, parentBox);

		if (element instanceof HasChildren hasChildren) {
			renderChildren(graphics, finalizedBox, hasChildren.getChildren(), element instanceof HasFlow hasFlow ? hasFlow.getFlow() : flow, null);
		}

		if (element instanceof Clickable clickable && (
			clickable.onClick().isPresent() ||
				clickable.onMouseEnter().isPresent() ||
				clickable.onMouseExit().isPresent()
		)) {
			mouseCollisionHandler.registerBox(finalizedBox);
		}

		return finalizedBox;
	}
}