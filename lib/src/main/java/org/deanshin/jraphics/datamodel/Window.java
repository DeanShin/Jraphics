package org.deanshin.jraphics.datamodel;

import org.deanshin.jraphics.internal.IWindowRenderer;
import org.deanshin.jraphics.internal.WindowRenderer;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Window {
	private final Frame frame;
	private final List<Consumer<Window>> windowDisposedConsumers;
	private final IWindowRenderer windowRenderer;
	private Dimensions<Size.Pixel, Size.Pixel> dimensions;
	private WindowState state;
	private List<Element> children;

	public Window(Dimensions<Size.Pixel, Size.Pixel> dimensions) {
		this.dimensions = dimensions;
		Window thisWindow = this;
		this.frame = new JFrame() {
			@Override
			public void paint(Graphics g) {
				windowRenderer.render(thisWindow, (Graphics2D) g);
			}
		};
		this.windowDisposedConsumers = new ArrayList<>();
		this.state = WindowState.INACTIVE;
		this.children = new ArrayList<>();
		this.windowRenderer = new WindowRenderer();
	}

	public Window(Size.Pixel width, Size.Pixel height) {
		this(Dimensions.builder().width(width).height(height).build());
	}

	public Dimensions<Size.Pixel, Size.Pixel> getDimensions() {
		return this.dimensions;
	}

	public Window setDimensions(Dimensions<Size.Pixel, Size.Pixel> dimensions) {
		this.dimensions = dimensions;
		this.frame.setSize(dimensions.getWidth().getPixels(), dimensions.getHeight().getPixels());
		return this;
	}

	public Frame getFrame() {
		return this.frame;
	}

	public Window setDimensions(Size.Pixel width, Size.Pixel height) {
		return setDimensions(new Dimensions<>(width, height));
	}

	public Window setWidth(Size.Pixel width) {
		return this.setDimensions(this.dimensions.withWidth(width));
	}

	public Window setHeight(Size.Pixel height) {
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

	public Window children(List<Element> children) {
		this.children = children;
		return this;
	}

	protected void activate() {
		if (this.state == WindowState.ACTIVATING || this.state == WindowState.ACTIVE) {
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
		if (this.state == WindowState.DEACTIVATING || this.state == WindowState.INACTIVE) {
			return;
		}
		this.state = WindowState.DEACTIVATING;
		this.frame.dispose();
		windowDisposedConsumers.forEach(windowDisposedConsumer -> windowDisposedConsumer.accept(this));
		this.state = WindowState.INACTIVE;
	}

	public List<Element> getChildren() {
		return children;
	}

	private enum WindowState {
		DEACTIVATING,
		INACTIVE,
		ACTIVATING,
		ACTIVE,
	}
}
