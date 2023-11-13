package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Clickable;

import javax.swing.event.MouseInputListener;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MouseCollisionHandler implements MouseInputListener {
	private final List<FinalizedBox> collisionBoxes;
	private Point prevPoint;


	MouseCollisionHandler() {
		this.collisionBoxes = new ArrayList<>();
	}

	void registerBox(FinalizedBox box) {
		collisionBoxes.add(box);
	}

	void reset() {
		collisionBoxes.clear();
	}

	private boolean isInside(Point point, FinalizedBox box) {
		return box.x().getPixels() <= point.getX() && point.getX() <= box.x().plus(box.width()).getPixels()
			&& box.y().getPixels() <= point.getY() && point.getY() <= box.y().plus(box.height()).getPixels();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (FinalizedBox collisionBox : collisionBoxes) {
			if (collisionBox.element() instanceof Clickable clickable && isInside(e.getPoint(), collisionBox)) {
				clickable.onClick().ifPresent(o -> o.accept(e));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (FinalizedBox collisionBox : collisionBoxes) {
			if (!(collisionBox.element() instanceof Clickable clickable)) {
				continue;
			}
			if (isInside(e.getPoint(), collisionBox) && !isInside(prevPoint, collisionBox)) {
				clickable.onMouseEnter().ifPresent(o -> o.accept(e));
			} else if (!isInside(e.getPoint(), collisionBox) && isInside(prevPoint, collisionBox)) {
				clickable.onMouseExit().ifPresent(o -> o.accept(e));
			}
		}
		prevPoint = e.getPoint();
	}
}
