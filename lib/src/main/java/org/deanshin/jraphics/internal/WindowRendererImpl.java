package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.*;

import javax.annotation.Nullable;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowRendererImpl implements WindowRenderer {
	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends Element>, Renderer<? extends Element>> renderers;

	public WindowRendererImpl() {
		RenderingUtilService renderingUtilService = new RenderingUtilServiceImpl();
		this.renderers = Stream.of(
			new TextRenderer(),
			new AbsoluteBoxRenderer(renderingUtilService),
			new RelativeBoxRenderer(renderingUtilService)
		).collect(Collectors.toMap(Renderer::getElementClass, r -> r));
	}

	public void render(Window window, Graphics2D graphics) {
		FinalizedBox finalizedWindowBox = new FinalizedBox(
			Size.ZERO,
			Size.ZERO,
			window.getDimensions().getWidth(),
			window.getDimensions().getHeight(),
			null
		);
		renderChildren(graphics, finalizedWindowBox, window.getChildren());
	}

	private void renderChildren(Graphics2D graphics, FinalizedBox parentBox, List<Element> children) {
		// AbsoluteBoxes are rendered in a separate pass, since they are all independently positioned from each other.
		for (Element child : children) {
			if (child instanceof AbsoluteBox) {
				renderElement(graphics, child, parentBox, null);
			}
		}

		FinalizedBox prev = null;
		for (Element child : children) {
			if (!(child instanceof AbsoluteBox)) {
				prev = renderElement(graphics, child, parentBox, prev);
			}
		}
	}

	private <T extends Element> FinalizedBox renderElement(
		Graphics2D graphics,
		T element,
		FinalizedBox parentBox,
		@Nullable FinalizedBox previousBox
	) {
		@SuppressWarnings("unchecked") Renderer<T> renderer = (Renderer<T>) renderers.get(element.getClass());
		FinalizedBox finalizedBox = renderer.getBounds(element, graphics, parentBox, previousBox);
		renderer.render(element, graphics, finalizedBox, parentBox);

		if (element instanceof HasChildren hasChildren) {
			renderChildren(graphics, finalizedBox, hasChildren.getChildren());
		}

		return finalizedBox;
	}


}