package org.deanshin.jraphics.datamodel;


import org.deanshin.jraphics.internal.StateManager;

public abstract class Component<PROPERTIES, STATE extends Record> implements Element.HasSiblings, HasChildren, Element {
	private final String key;
	protected PROPERTIES properties;

	protected Component(PROPERTIES properties, String key) {
		this.properties = properties;
		this.key = key;
		if (StateManager.getInstance().getValue(key) == null) {
			StateManager.getInstance().setValue(key, initializeState());
		}
	}

	protected abstract STATE initializeState();

	protected STATE updateState(STATE state) {
		StateManager.getInstance().setValue(key, state);
		StateManager.getInstance().rerender();
		return state;
	}

	protected STATE getState() {
		return StateManager.getInstance().getValue(key);
	}
}
