package org.deanshin.jraphics;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Window {
    private Rectangle<Size.PixelSize, Size.PixelSize> rectangle;
    private final Frame frame;
    private final List<Consumer<Window>> windowDisposedConsumers;
    private WindowState state;

    public Window(Rectangle<Size.PixelSize, Size.PixelSize> rectangle) {
        this.rectangle = rectangle;
        this.frame = new Frame();
        this.windowDisposedConsumers = new ArrayList<>();
        this.state = WindowState.INACTIVE;
    }

    public Window(Size.PixelSize width, Size.PixelSize height) {
        this(new Rectangle<>(width, height));
    }

    public Window setRectangle(Rectangle<Size.PixelSize, Size.PixelSize> rectangle) {
        this.rectangle = rectangle;
        this.frame.setSize(rectangle.getWidth().getPixels(), rectangle.getHeight().getPixels());
        return this;
    }

    public Window setRectangle(Size.PixelSize width, Size.PixelSize height) {
        return setRectangle(new Rectangle<>(width, height));
    }

    public Window setWidth(Size.PixelSize width) {
        return this.setRectangle(this.rectangle.withWidth(width));
    }

    public Window setHeight(Size.PixelSize height) {
        return this.setRectangle(this.rectangle.withHeight(height));
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
        this.frame.setSize(rectangle.getWidth().getPixels(), rectangle.getHeight().getPixels());
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
