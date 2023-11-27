package org.deanshin.jraphics.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The Application class is the top-level class for the Jraphics application. You should use the Application class to
 * manage the windows of your application.
 */
public class Application {
	private final List<Window> windows;
	private final Map<Window, Consumer<Window>> windowDeactivatedConsumerMap;

	public Application() {
		this.windows = new ArrayList<>();
		this.windowDeactivatedConsumerMap = new HashMap<>();
	}

	/**
	 * Add a window to the application. This will open the new window on the computer.
	 *
	 * @return the application
	 */
	public Application addWindow(Window window) {
		if (this.windows.contains(window)) {
			return this;
		}

		this.windows.add(window);
		this.windowDeactivatedConsumerMap.put(window, (removedWindow) -> {

		});
		window.addWindowDeactivatedConsumer(this.windowDeactivatedConsumerMap.get(window));
		window.activate();
		return this;
	}

	/**
	 * Remove a window from the application. This will close the window on the computer.
	 * If no windows remain after the window is removed, the application is terminated.
	 *
	 * @return the application
	 */
	public Application removeWindow(Window window) {
		if (!this.windows.contains(window)) {
			return this;
		}

		window.removeWindowDeactivatedConsumer(this.windowDeactivatedConsumerMap.get(window));
		this.windows.remove(window);
		window.deactivate();

		if (this.windows.isEmpty()) {
			System.exit(0);
		}

		return this;
	}
}
