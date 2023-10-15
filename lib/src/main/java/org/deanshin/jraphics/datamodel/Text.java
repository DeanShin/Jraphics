package org.deanshin.jraphics.datamodel;

import java.awt.Font;

public class Text implements Element.HasNoSiblings {
	private final String text;
	private final Color color;
	private final Font font;

	public Text(String text, Color color, Font font) {
		this.text = text;
		this.color = color;
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont() {
		return font;
	}
}
