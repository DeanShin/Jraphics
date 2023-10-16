package org.deanshin.jraphics.datamodel;

import java.awt.Font;

public class Text implements Element.HasNoSiblings {
	private final String text;
	private final Color color;
	private final Font font;
	private final Size.Pixel lineHeight;

	public Text(String text, Color color, Font font, Size.Pixel lineHeight) {
		this.text = text;
		this.color = color;
		this.font = font;
		this.lineHeight = lineHeight;
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

	public Size.Pixel getLineHeight() {
		return lineHeight;
	}
}
