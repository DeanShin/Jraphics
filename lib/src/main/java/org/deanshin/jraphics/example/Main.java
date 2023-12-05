package org.deanshin.jraphics.example;

import org.deanshin.jraphics.datamodel.Application;
import org.deanshin.jraphics.datamodel.Window;

import java.util.List;

import static org.deanshin.jraphics.datamodel.Size.pixel;

public class Main {
	public static void main(String[] args) {
		// Create an application with a single 500x500 window
		Application application = new Application();
		Window window = new Window(pixel(500), pixel(500))
			.children(List.of(
				// The window's only child component is the ScreenManager.
				new ScreenManager()
			));
		application.addWindow(window);
	}
}
