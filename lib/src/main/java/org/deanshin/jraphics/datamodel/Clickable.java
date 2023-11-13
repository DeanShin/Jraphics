package org.deanshin.jraphics.datamodel;

import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.function.Consumer;

public interface Clickable {
	Optional<Consumer<MouseEvent>> onClick();

	Optional<Consumer<MouseEvent>> onMouseEnter();

	Optional<Consumer<MouseEvent>> onMouseExit();
}
