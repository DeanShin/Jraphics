package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * An element that can be clicked by the user
 */
public interface Clickable {
	Optional<Consumer<MouseEvent>> onClick();

	Optional<Consumer<MouseEvent>> onMouseEnter();

	Optional<Consumer<MouseEvent>> onMouseExit();
}
