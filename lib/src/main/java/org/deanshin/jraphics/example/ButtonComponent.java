package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * The ButtonComponent takes in a string 'text' in its properties, which is the text rendered on the button.
 * Additionally, it takes in an onClick callback, which is called when the button is clicked.
 */
public class ButtonComponent extends Component<ButtonComponent.Properties, ButtonComponent.State> {

	protected ButtonComponent(Properties properties) {
		super(properties, "");
	}

	@Override
	protected State initializeState() {
		return new State();
	}

	@Override
	public List<Element> getChildren() {
		return List.of(
			RelativeBox.builder()
				.content(content ->
					content.dimensions(dimensions ->
							dimensions.width(Size.percentage(100)).height(Size.percentage(100)))
						.color(Color.WHITE)
				)
				.border(border ->
					border.all(all ->
						all.size(Size.pixel(1))
							.color(Color.BLACK)
					)
				)
				.child(Text.builder()
					.text(properties.text)
					.align(Text.Align.CENTER)
					.build()
				)
				.onClick(properties.onClick)
				.build()
		);
	}

	public record Properties(String text, Consumer<MouseEvent> onClick) {

	}

	protected record State() {

	}
}
