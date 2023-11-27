package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

public class ClickCounterComponent extends Component<ClickCounterComponent.Properties, ClickCounterComponent.State> {
	public ClickCounterComponent(String key) {
		super(new ClickCounterComponent.Properties(), key);
	}

	@Override
	protected State initializeState() {
		return new State(0);
	}

	@Override
	public List<Element> getChildren() {
		return List.of(
			RelativeBox.builder()
				.content(content ->
					content.dimensions(dim -> dim.width(Size.percentage(100)).height(Size.percentage(100)))
						.color(Color.WHITE)
				)
				.border(border -> border.all(b -> b.size(Size.pixel(1)).color(Color.BLACK)))
				.child(Text.builder()
					.align(Text.Align.CENTER)
					.text("" + getState().value)
					.build())
				.onClick(mouseEvent -> updateState(new State(getState().value + 1)))
				.build()
		);
	}

	public record Properties() {

	}

	protected record State(int value) {

	}
}
