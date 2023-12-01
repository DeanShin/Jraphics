package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class RelativeBoxDemoScreen extends Component<RelativeBoxDemoScreen.Properties, RelativeBoxDemoScreen.State> {

	public RelativeBoxDemoScreen() {
		super(new RelativeBoxDemoScreen.Properties(), "");
	}

	@Override
	protected RelativeBoxDemoScreen.State initializeState() {
		return new RelativeBoxDemoScreen.State();
	}

	@Override
	public List<Element> getChildren() {
		return List.of(
			RelativeBox.builder()
				.margin(m -> m.top(Size.pixel(100)).left(Size.pixel(100)))
				.content(c -> c.color(Color.BLACK)
					.width(Size.pixel(100))
					.height(Size.pixel(100))
				)
				.children(
					RelativeBox.builder()
						.margin(m -> m.top(Size.pixel(25)).left(Size.pixel(25)))
						.content(c -> c.color(Color.WHITE)
							.width(Size.pixel(50))
							.height(Size.pixel(25))
						)
						.build(),
					RelativeBox.builder()
						.margin(m -> m.top(Size.pixel(25)).left(Size.pixel(25)))
						.content(c -> c.color(Color.WHITE)
							.width(Size.pixel(25))
							.height(Size.pixel(50))
						)
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
