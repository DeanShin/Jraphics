package org.deanshin.jraphics;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Window {
    private Size size;
    private final Frame frame;
    private final List<Consumer<Window>> windowDisposedConsumers;
    private WindowState state;

    public Window(Size size) {
        this.size = size;
        this.frame = new Frame();
        this.windowDisposedConsumers = new ArrayList<>();
        this.state = WindowState.INACTIVE;
    }

    public Window(int width, int height) {
        this(new Size(width, height));
    }

    public Window setSize(Size size) {
        this.size = size;
        this.frame.setSize(size.toAWTDimension());
        return this;
    }

    public Window setSize(int width, int height) {
        this.size = new Size(width, height);
        this.frame.setSize(width, height);
        return this;
    }

    public Window setWidth(int width) {
        return this.setSize(new Size(width, this.size.getHeight()));
    }

    public Window setHeight(int height) {
        return this.setSize(new Size(this.size.getWidth(), height));
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
        if(state == WindowState.ACTIVATING || state == WindowState.ACTIVE) {
            return;
        }
        this.state = WindowState.ACTIVATING;
        this.frame.setSize(size.toAWTDimension());
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
        if(state == WindowState.DEACTIVATING || state == WindowState.INACTIVE) {
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
