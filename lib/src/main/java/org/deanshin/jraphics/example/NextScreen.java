package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

import static org.deanshin.jraphics.datamodel.Size.pixel;

public class NextScreen extends Component<NextScreen.Properties, NextScreen.State> {
	protected NextScreen(Properties properties) {
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
				.content(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(100)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(100)))
				.child(Text.builder().text("Next Screen").align(Text.Align.CENTER).color(Color.BLACK).build())
				.build(),
			RelativeBox.builder()
				.content(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(50)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(100)))
				.flow(ElementFlow.HORIZONTAL)
				.children(
					new ClickCounterComponent("click_counter_1"),
					new ClickCounterComponent("click_counter_2")
				)
				.build(),
			RelativeBox.builder()
				.content(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(50)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(100)))
				.flow(ElementFlow.HORIZONTAL)
				.children(
					new ButtonComponent(new ButtonComponent.Properties(
						"Back",
						(ignored) -> properties.onBackButtonClicked.run()
					))
				)
				.build()
		);
	}

	public record Properties(Runnable onBackButtonClicked) {

	}

	public record State() {

	}
}
