package org.deanshin.jraphics.internal;

import org.deanshin.jraphics.datamodel.ElementFlow;
import org.deanshin.jraphics.datamodel.Size;
import org.deanshin.jraphics.datamodel.Text;

import javax.annotation.Nullable;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class TextRenderer implements Renderer<Text> {
	private static final String LINE_BREAK = "\n";
	private static final String DELIMITER = " ";

	private final RenderingUtilService renderingUtilService;

	TextRenderer(RenderingUtilService renderingUtilService) {
		this.renderingUtilService = renderingUtilService;
	}

	public Class<Text> getElementClass() {
		return Text.class;
	}

	@Override
	public FinalizedBox getBounds(Text element, Graphics2D graphics2D, FinalizedBox parentBox, @Nullable FinalizedBox previousSiblingBox, ElementFlow flow) {
		return renderingUtilService.contentBox(parentBox, parentBox, element);
	}

	@Override
	public void render(Text element, Graphics2D graphics, FinalizedBox finalizedBox, FinalizedBox parentBox) {
		graphics.setFont(element.getFont());
		graphics.setColor(element.getColor().toAwtColor());
		graphics.setFont(element.getFont());
		FontMetrics fontMetrics = graphics.getFontMetrics();
		List<String> lines = breakIntoLines(element.getText(), finalizedBox.width().getPixels(), fontMetrics);
		Size.Pixel yOffset = Size.pixel(fontMetrics.getAscent());
		for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
			int lineWidth = fontMetrics.stringWidth(lines.get(lineIndex));
			graphics.drawString(
				lines.get(lineIndex),
				getLineX(element, finalizedBox, lineWidth),
				finalizedBox.y().plus(element.getLineHeight().times(lineIndex)).plus(yOffset).getPixels()
			);
		}
	}

	private List<String> breakIntoLines(String label, int maxWidth, FontMetrics fontMetrics) {
		String[] initialLines = label.split(LINE_BREAK);
		return Arrays.stream(initialLines)
			.flatMap(line -> {
				String[] words = line.split(DELIMITER);
				return packWordsIntoLine(maxWidth, fontMetrics, Arrays.stream(words).toList(), 0);
			})
			.toList();
	}

	private Stream<String> packWordsIntoLine(int maxWidth, FontMetrics fontMetrics, List<String> words, int wordIdx) {
		if (wordIdx >= words.size()) {
			return Stream.of();
		}

		// Use binary search to put the maximum number of words that we can pack onto one line
		int l = wordIdx;
		int r = words.size();
		while (l < r) {
			int m = (l + r) >> 1;
			int actualWidth = fontMetrics.stringWidth(String.join(DELIMITER, words.subList(wordIdx, m)));
			if (actualWidth <= maxWidth) {
				l = m + 1;
			} else {
				r = m;
			}
		}

		int newWordIdx = Math.max(
			l - 1,
			// Always include at least one word on the line. This means that a super long word will
			// overflow.
			wordIdx + 1
		);
		return Stream.concat(
			Stream.of(String.join(DELIMITER, words.subList(wordIdx, newWordIdx))),
			packWordsIntoLine(maxWidth, fontMetrics, words, newWordIdx)
		);
	}

	private int getLineX(Text element, FinalizedBox finalizedBox, int lineWidth) {
		return switch (element.getAlign()) {
			case LEFT -> finalizedBox.x().getPixels();
			case CENTER -> finalizedBox.x()
				.plus(finalizedBox.width()
					.minus(Size.pixel(lineWidth))
					.times(0.5)
				)
				.getPixels();
			case RIGHT -> finalizedBox.x()
				.plus(finalizedBox.width())
				.minus(Size.pixel(lineWidth))
				.getPixels();
		};
	}
}
