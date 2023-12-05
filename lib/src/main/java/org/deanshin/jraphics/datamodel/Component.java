package org.deanshin.jraphics.datamodel;


import org.deanshin.jraphics.internal.StateManager;

/**
 * The base abstract class that all components in the application must inherit from.
 * <p>
 * A component can be thought of as a reusable set of visual elements. If, say, you have multiple buttons in your program
 * that use the same layout of boxes, then a component is an extremely effective way of grouping those boxes together into
 * a reusable element.
 * </p>
 * <p>
 * Each component has two inputs that affects how it renders: the properties and the state.
 * </p>
 * <p>
 * Properties can be thought of as the inputs to the function. If you have two buttons with different copy, then you
 * can add the text of the button as a property for the component. See
 * {@link org.deanshin.jraphics.example.ButtonComponent} for an example.
 * </p>
 * <p>
 * State on the other hand represents internal variables specific to the component. For example, if you want
 * to have a variable that detects how many times a button was pressed, then you can add that variable to the
 * component's state. See {@link org.deanshin.jraphics.example.ClickCounterComponent} for an example.
 * </p>
 *
 * @param <PROPERTIES> The property class / record.
 * @param <STATE>      The state class / record.
 */
public abstract class Component<PROPERTIES, STATE extends Record> implements Element.HasSiblings, HasChildren, Element {
	private final String key;
	protected PROPERTIES properties;

	/**
	 * The base constructor for the component.
	 *
	 * @param key The key used to store the state of the component. If two component share the same key,
	 *            they will share the same state.
	 */
	protected Component(PROPERTIES properties, String key) {
		this.properties = properties;
		this.key = key;
		if (StateManager.getInstance().getValue(key) == null) {
			StateManager.getInstance().setValue(key, initializeState());
		}
	}

	/**
	 * Function used to initialize the state of the component.
	 */
	protected abstract STATE initializeState();

	/**
	 * Update the state of the component. Calling this function will rerender the application.
	 */
	protected STATE updateState(STATE state) {
		StateManager.getInstance().setValue(key, state);
		StateManager.getInstance().rerender();
		return state;
	}

	/**
	 * Retrieve the state of the current component.
	 */
	protected STATE getState() {
		return StateManager.getInstance().getValue(key);
	}
}
