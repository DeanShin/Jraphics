package org.deanshin.jraphics;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Window {
    private Dimensions<Size.PixelSize, Size.PixelSize> dimensions;
    private final Frame frame;
    private final List<Consumer<Window>> windowDisposedConsumers;
    private WindowState state;

    public Window(Dimensions<Size.PixelSize, Size.PixelSize> dimensions) {
        this.dimensions = dimensions;
        this.frame = new Frame();
        this.windowDisposedConsumers = new ArrayList<>();
        this.state = WindowState.INACTIVE;
    }

    public Window(Size.PixelSize width, Size.PixelSize height) {
        this(new Dimensions<>(width, height));
    }

    public Window setDimensions(Dimensions<Size.PixelSize, Size.PixelSize> dimensions) {
        this.dimensions = dimensions;
        this.frame.setSize(dimensions.getWidth().getPixels(), dimensions.getHeight().getPixels());
        return this;
    }

    public Window setDimensions(Size.PixelSize width, Size.PixelSize height) {
        return setDimensions(new Dimensions<>(width, height));
    }

    public Window setWidth(Size.PixelSize width) {
        return this.setDimensions(this.dimensions.withWidth(width));
    }

    public Window setHeight(Size.PixelSize height) {
        return this.setDimensions(this.dimensions.withHeight(height));
    }

    public Window addWindowDeactivatedConsumer(Consumer<Window> windowDisposedConsumer) {
        this.windowDisposedConsumers.add(windowDisposedConsumer);
        return this;
    }

    public Window removeWindowDeactivatedConsumer(Consumer<Window> windowDisposedConsumer) {
        this.windowDisposedConsumers.remove(windowDisposedConsumer);
        return this;
    }

    protected void activate() {
        if(this.state == WindowState.ACTIVATING || this.state == WindowState.ACTIVE) {
            return;
        }
        this.state = WindowState.ACTIVATING;
        this.frame.setSize(dimensions.getWidth().getPixels(), dimensions.getHeight().getPixels());
        this.frame.setVisible(true);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deactivate();
            }
        });
        this.state = WindowState.ACTIVE;
    }

    protected void deactivate() {
        if(this.state == WindowState.DEACTIVATING || this.state == WindowState.INACTIVE) {
            return;
        }
        this.state = WindowState.DEACTIVATING;
        this.frame.dispose();
        windowDisposedConsumers.forEach(windowDisposedConsumer -> windowDisposedConsumer.accept(this));
        this.state = WindowState.INACTIVE;
    }

    private enum WindowState {
        DEACTIVATING,
        INACTIVE,
        ACTIVATING,
        ACTIVE,
    }
}
