package org.deanshin.jraphics;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

public interface Size {
    /**
     * Construct a pixel-based size.
     * @param pixels the number of pixels. Must be greater than or equal to 0.
     * @return the size in pixels
     */
    static PixelSize pixel(int pixels) {
        return new PixelSize(pixels);
    }

    /**
     * Construct a percentage-based size.
     * @param percentage the percentage, with 100 meaning that the object should take up the full width of its container element. Must be greater than or equal to 0.
     * @return the size as a percentage of the parent's size
     */
    static PercentageSize percentage(double percentage) {
        return new PercentageSize(percentage);
    }

    /**
     * Construct a fractional-based size.
     * @param numerator the numerator of the container element that the object should take up.
     * @return the size as a numerator of the parent's size
     */
    static FractionalSize fractional(int numerator) {
        return new FractionalSize(numerator);
    }

    /**
     * Construct an auto size.
     * @return an auto size.
     */
    static AutoSize auto() {
        return new AutoSize();
    }
    
    static <MIN extends Size, MAX extends Size> RangeSize<MIN, MAX> range(MIN minUnit, MAX maxUnit) {
        return new RangeSize<>(minUnit, maxUnit);
    }

    static MinSize min(List<Size> candidates) {
        return new MinSize(candidates);
    }

    static MinSize min(Size... candidates) {
        return new MinSize(Arrays.stream(candidates).toList());
    }

    static MaxSize max(List<Size> candidates) {
        return new MaxSize(candidates);
    }

    static MaxSize max(Size... candidates) {
        return new MaxSize(Arrays.stream(candidates).toList());
    }

    static PixelSize ZERO = new PixelSize(0);

    class PixelSize implements Size {
        private final int pixels;

        private PixelSize(int pixels) {
            Preconditions.checkState(pixels >= 0);
            this.pixels = pixels;
        }

        public int getPixels() {
            return pixels;
        }
    }

    class PercentageSize implements Size {
        private final double percentage;

        private PercentageSize(double percentage) {
            Preconditions.checkState(percentage >= 0);
            this.percentage = percentage;
        }

        public double getPercentage() {
            return percentage;
        }
    }

    class FractionalSize implements Size {
        private final int numerator;

        private FractionalSize(int numerator) {
            Preconditions.checkState(numerator > 0);
            this.numerator = numerator;
        }

        public int getNumerator() {
            return numerator;
        }
    }

    class AutoSize implements Size {
        private AutoSize() {

        }
    }

    class RangeSize<MIN extends Size, MAX extends Size> implements Size {
        private final MIN minUnit;
        private final MAX maxUnit;

        private RangeSize(MIN minUnit, MAX maxUnit) {
            this.minUnit = minUnit;
            this.maxUnit = maxUnit;
        }

        public MIN getMinUnit() {
            return minUnit;
        }

        public MAX getMaxUnit() {
            return maxUnit;
        }
    }

    class MinSize implements Size {
        private final List<Size> candidates;

        private MinSize(List<Size> candidates) {
            Preconditions.checkState(!candidates.isEmpty());
            this.candidates = candidates;
        }

        public List<Size> getCandidates() {
            return candidates;
        }
    }

    class MaxSize implements Size {
        private final List<Size> candidates;

        private MaxSize(List<Size> candidates) {
            Preconditions.checkState(!candidates.isEmpty());
            this.candidates = candidates;
        }

        public List<Size> getCandidates() {
            return candidates;
        }
    }
}
