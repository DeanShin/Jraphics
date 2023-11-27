package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;

import static org.deanshin.jraphics.datamodel.Size.pixel;

public class MainScreen extends Component<MainScreen.Properties, MainScreen.State> {
	protected MainScreen(Properties properties) {
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
				.box(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(100)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(100)))
				.child(Text.builder().text("Main Screen").align(Text.Align.CENTER).color(Color.BLACK).build())
				.build(),
			RelativeBox.builder()
				.box(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(50)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(200)))
				.children(new ButtonComponent(new ButtonComponent.Properties(
					"Next",
					(ignored) -> properties.onNextButtonClicked.run()
				)))
				.build()
		);
	}

	public record Properties(Runnable onNextButtonClicked) {

	}

	public record State() {

	}
}