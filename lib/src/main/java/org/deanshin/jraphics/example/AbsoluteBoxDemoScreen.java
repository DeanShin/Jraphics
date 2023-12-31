package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

/**
 * A class showcasing how to use absolute boxes.
 */
public class AbsoluteBoxDemoScreen extends Component<AbsoluteBoxDemoScreen.Properties, AbsoluteBoxDemoScreen.State> {

	public AbsoluteBoxDemoScreen() {
		super(new Properties(), "");
	}

	@Override
	protected State initializeState() {
		return new State();
	}

	@Override
	public List<Element> getChildren() {
		// Absolute boxes use an x and y coordinate that determine how far from the top left of the parent element
		// the element should be drawn.
		return List.of(
			AbsoluteBox.builder()
				.x(Size.pixel(100))
				.y(Size.pixel(100))
				.content(c -> c.color(Color.BLACK)
					.width(Size.pixel(100))
					.height(Size.pixel(100))
				)
				.children(
					AbsoluteBox.builder()
						.x(Size.pixel(25))
						.y(Size.pixel(25))
						.content(c -> c.color(Color.WHITE)
							.width(Size.pixel(50))
							.height(Size.pixel(25))
						)
						.build(),
					AbsoluteBox.builder()
						.x(Size.pixel(75))
						.y(Size.pixel(50))
						.content(c -> c.color(Color.WHITE)
							.width(Size.pixel(25))
							.height(Size.pixel(50)))
						.build()
				)
				.build()
		);
	}

	public record Properties() {
	}

	public record State() {
	}
}
