package org.deanshin.jraphics.datamodel;

import java.awt.Font;

public class Text implements Element.HasNoSiblings {
	private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

	private final String text;
	private final Color color;
	private final Font font;
	private final Size.Pixel lineHeight;
	private final Align align;

	private Text(String text, Color color, Font font, Size.Pixel lineHeight, Align align) {
		this.text = text;
		this.color = color;
		this.font = font;
		this.lineHeight = lineHeight;
		this.align = align;
	}

	public static Builder builder() {
		return new Builder();
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

	public Align getAlign() {
		return align;
	}

	public enum Align {
		LEFT,
		CENTER,
		RIGHT
	}

	public static class Builder {
		private String text;
		private Color color;
		private Font font;
		private Size.Pixel lineHeight;
		private Align align;

		public Builder() {
			this.text = "";
			this.color = Color.BLACK;
			this.font = DEFAULT_FONT;
			this.lineHeight = Size.pixel(12);
			this.align = Align.LEFT;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder color(Color color) {
			this.color = color;
			return this;
		}

		public Builder font(Font font) {
			this.font = font;
			return this;
		}

		public Builder lineHeight(Size.Pixel lineHeight) {
			this.lineHeight = lineHeight;
			return this;
		}

		public Builder align(Align align) {
			this.align = align;
			return this;
		}

		public Text build() {
			return new Text(text, color, font, lineHeight, align);
		}
	}
}
