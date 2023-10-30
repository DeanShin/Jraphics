package org.deanshin.jraphics.datamodel;

import java.util.function.Consumer;

public abstract class Component<PROPERTIES, STATE extends Record> implements Element.HasSiblings, HasChildren, Element {
	protected PROPERTIES properties;
	protected STATE state;
	private Consumer<Component<?, ?>> onComponentStateChanged;

	protected Component(PROPERTIES properties) {
		this.properties = properties;
	}

	protected abstract void initializeState();

	protected STATE updateState(STATE state) {
		this.state = state;
		onComponentStateChanged.accept(this);
		return this.state;
	}

	public Component<?, ?> addComponent(Component<?, ?> component) {
		component.setOnComponentStateChanged(onComponentStateChanged);
		return component;
	}

	protected void setOnComponentStateChanged(Consumer<Component<?, ?>> onComponentStateChanged) {
		this.onComponentStateChanged = onComponentStateChanged;
	}
}
