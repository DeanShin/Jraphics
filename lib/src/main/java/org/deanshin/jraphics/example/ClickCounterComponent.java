package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

/**
 * The ClickCounterComponent takes in String 'key', which is used to store the state of the component.
 * It functions by leveraging state--the 'value' parameter in the state stores how many times the component has been
 * clicked, which is then rendered as a Text object on the component.
 */
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
				// Render the number of times that the component has been clicked.
				.child(Text.builder()
					.align(Text.Align.CENTER)
					.text("" + getState().value)
					.build())
				// When the component is clicked, increment the number of times that it has been clicked by 1.
				.onClick(mouseEvent -> updateState(new State(getState().value + 1)))
				.build()
		);
	}

	public record Properties() {

	}

	protected record State(int value) {

	}
}
