package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.Element;
import org.deanshin.jraphics.datamodel.Size;

import javax.annotation.Nullable;

record FinalizedBox(Size.Pixel x, Size.Pixel y, Size.Pixel width, Size.Pixel height,
                    @Nullable Element element) {
	@Override
	public Size.Pixel x() {
		return x;
	}

	@Override
	public Size.Pixel y() {
		return y;
	}

	@Override
	public Size.Pixel width() {
		return width;
	}

	@Override
	public Size.Pixel height() {
		return height;
	}

	@Override
	@Nullable
	public Element element() {
		return element;
	}
}
