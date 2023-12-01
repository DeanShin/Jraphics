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
		return switch (getState().screen) {
			case MAIN -> List.of(new MainScreen(
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
