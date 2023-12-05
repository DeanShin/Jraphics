package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.*;

import java.util.List;
import java.util.function.Consumer;

import static org.deanshin.jraphics.datamodel.Size.pixel;

/**
 * The MainScreen renders a set of buttons, which on click call an onScreenChangeRequested callback with each button's
 * corresponding screen as the parameter.
 */
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
				.content(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(100)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(100)))
				.child(Text.builder().text("Main Screen").align(Text.Align.CENTER).color(Color.BLACK).build())
				.build(),
			RelativeBox.builder()
				.content(box -> box.color(Color.WHITE)
					.dimensions(dim -> dim.width(Size.percentage(100)).height(Size.pixel(50))))
				.margin(margin -> margin.top(pixel(200)))
				.flow(ElementFlow.HORIZONTAL)
				.children(
					RelativeBox.builder()
						.content(box -> box.color(Color.WHITE)
							.height(Size.percentage(100))
							.width(Size.percentage(33))
						)
						.children(new ButtonComponent(new ButtonComponent.Properties(
							"Next",
							// When the next button is clicked, call the onScreenChangeRequested callback with the NEXT screen as a parameter
							(ignored) -> properties.onScreenChangeRequested.accept(ScreenManager.Screen.NEXT)
						)))
						.build(),
					RelativeBox.builder()
						.content(box -> box.color(Color.WHITE)
							.height(Size.percentage(100))
							.width(Size.percentage(33))
						)
						.children(new ButtonComponent(new ButtonComponent.Properties(
							"Absolute",
							// When the absolute button is clicked, call the onScreenChangeRequested callback with the ABSOLUTE_BOX_DEMO screen as a parameter
							(ignored) -> properties.onScreenChangeRequested.accept(ScreenManager.Screen.ABSOLUTE_BOX_DEMO)
						)))
						.build(),
					RelativeBox.builder()
						.content(box -> box.color(Color.WHITE)
							.height(Size.percentage(100))
							.width(Size.percentage(33))
						)
						.children(new ButtonComponent(new ButtonComponent.Properties(
							"Relative",
							// When the relative button is clicked, call the onScreenChangeRequested callback with the RELATIVE_BOX_DEMO screen as a parameter
							(ignored) -> properties.onScreenChangeRequested.accept(ScreenManager.Screen.RELATIVE_BOX_DEMO)
						)))
						.build()
				)
				.build()
		);
	}

	public record Properties(Consumer<ScreenManager.Screen> onScreenChangeRequested) {

	}

	public record State() {

	}
}
