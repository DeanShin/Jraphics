package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.Component;
import org.deanshin.jraphics.datamodel.Element;

import java.util.List;

/**
 * The ScreenManager is this example application's top level component which determines which screen to show to the user.
 */
public class ScreenManager extends Component<ScreenManager.Properties, ScreenManager.State> {
	public ScreenManager() {
		super(new Properties(), "");
	}

	@Override
	protected State initializeState() {
		// The default screen is the MAIN screen
		return new State(Screen.MAIN);
	}

	@Override
	public List<Element> getChildren() {
		// Based on the state of the component, show a different screen.
		return switch (getState().screen) {
			case MAIN -> List.of(new MainScreen(
				// Provide a callback to the MainScreen can be called to update the state of this ScreenManager component.
				// When updateState() is called, the application is re-rendered, and getChildren() will be called again,
				// allowing the screen to switch.
				new MainScreen.Properties((screen) -> updateState(new State(screen)))
			));
			case NEXT -> List.of(new NextScreen(
				new NextScreen.Properties(() -> updateState(new State(Screen.MAIN)))
			));
			case ABSOLUTE_BOX_DEMO -> List.of(new AbsoluteBoxDemoScreen());
			case RELATIVE_BOX_DEMO -> List.of(new RelativeBoxDemoScreen());
		};
	}

	public enum Screen {
		MAIN,
		NEXT,
		ABSOLUTE_BOX_DEMO,
		RELATIVE_BOX_DEMO
	}

	public record State(Screen screen) {

	}

	public record Properties() {

	}
}
