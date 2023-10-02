package org.deanshin.jraphics.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Application {
	private final List<Window> windows;
	private final Map<Window, Consumer<Window>> windowDeactivatedConsumerMap;

	public Application() {
		this.windows = new ArrayList<>();
		this.windowDeactivatedConsumerMap = new HashMap<>();
	}

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
