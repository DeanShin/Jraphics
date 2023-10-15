package org.deanshin.jraphics.datamodel;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

public interface Size {
	Pixel ZERO = new Pixel(0);

	/**
	 * Construct a pixel-based size.
	 *
	 * @param pixels the number of pixels. Must be greater than or equal to 0.
	 * @return the size in pixels
	 */
	static Pixel pixel(int pixels) {
		return new Pixel(pixels);
	}

	/**
	 * Construct a percentage-based size.
	 *
	 * @param percentage the percentage, with 100 meaning that the object should take up the full width of its container element. Must be greater than or equal to 0.
	 * @return the size as a percentage of the parent's size
	 */
	static Percentage percentage(double percentage) {
		return new Percentage(percentage);
	}

	/**
	 * Construct a fractional-based size.
	 *
	 * @param numerator the numerator of the container element that the object should take up.
	 * @return the size as a numerator of the parent's size
	 */
	static Fractional fractional(int numerator) {
		return new Fractional(numerator);
	}

	static Min min(List<Size> candidates) {
		return new Min(candidates);
	}

	static Min min(Size... candidates) {
		return new Min(Arrays.stream(candidates).toList());
	}

	static Max max(List<Size> candidates) {
		return new Max(candidates);
	}

	static Max max(Size... candidates) {
		return new Max(Arrays.stream(candidates).toList());
	}

	class Pixel implements Size {
		private final int pixels;

		private Pixel(int pixels) {
			Preconditions.checkState(pixels >= 0);
			this.pixels = pixels;
		}

		public int getPixels() {
			return pixels;
		}

		public Pixel add(Pixel other) {
			return new Pixel(pixels + other.pixels);
		}

		public Pixel subtract(Pixel other) {
			return new Pixel(pixels - other.pixels);
		}

		public Pixel multiply(double other) {
			return new Pixel((int) Math.floor(((double) pixels) * other));
		}
	}

	class Percentage implements Size {
		private final double percentage;

		private Percentage(double percentage) {
			Preconditions.checkState(percentage >= 0);
			this.percentage = percentage;
		}

		public double getPercentage() {
			return percentage;
		}
	}

	class Fractional implements Size {
		private final int numerator;

		private Fractional(int numerator) {
			Preconditions.checkState(numerator > 0);
			this.numerator = numerator;
		}

		public int getNumerator() {
			return numerator;
		}
	}

	class Min implements Size {
		private final List<Size> candidates;

		private Min(List<Size> candidates) {
			Preconditions.checkState(!candidates.isEmpty());
			this.candidates = candidates;
		}

		public List<Size> getCandidates() {
			return candidates;
		}
	}

	class Max implements Size {
		private final List<Size> candidates;

		private Max(List<Size> candidates) {
			Preconditions.checkState(!candidates.isEmpty());
			this.candidates = candidates;
		}

		public List<Size> getCandidates() {
			return candidates;
		}
	}
}
