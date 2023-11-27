package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.Component;
import org.deanshin.jraphics.datamodel.Element;

import java.util.List;

public class ScreenManager extends Component<ScreenManager.Properties, ScreenManager.State> {
	public ScreenManager() {
		super(new Properties(), "");
	}

	@Override
	protected State initializeState() {
		return new State(Screen.MAIN);
	}

	@Override
	public List<Element> getChildren() {
		if (getState().screen == Screen.MAIN) {
			return List.of(new MainScreen(
				new MainScreen.Properties(() -> updateState(new State(Screen.NEXT)))
			));
		} else if (getState().screen == Screen.NEXT) {
			return List.of(new NextScreen(
				new NextScreen.Properties(() -> updateState(new State(Screen.MAIN)))
			));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private enum Screen {
		MAIN,
		NEXT
	}

	public record State(Screen screen) {

	}

	public record Properties() {

	}
}
