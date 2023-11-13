package org.deanshin.jraphics.internal;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
	private static StateManager instance;

	private final Map<String, Object> stateMap;
	private Runnable onStateChanged;

	public StateManager() {
		this.stateMap = new HashMap<>();
	}

	public static StateManager getInstance() {
		if (instance == null) {
			instance = new StateManager();
		}
		return instance;
	}

	public void setOnStateChanged(Runnable onStateChanged) {
		this.onStateChanged = onStateChanged;
	}

	public void registerStateIfNotPresent(String key, Object value) {
		if (stateMap.containsKey(key)) {
			return;
		}

		stateMap.put(key, value);
	}

	public <T> T getValue(String key) {
		//noinspection unchecked
		return (T) stateMap.get(key);
	}

	public <T> T setValue(String key, T value) {
		stateMap.put(key, value);
		return value;
	}

	public void rerender() {
		onStateChanged.run();
	}
}
